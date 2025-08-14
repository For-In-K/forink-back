package com.forink.forink.resume.application;

import com.forink.forink.global.error.BusinessException;
import static com.forink.forink.global.error.ErrorCode.FAILED_TO_GENERATE_BLOCKCHAIN_KEY;
import static com.forink.forink.global.error.ErrorCode.FAILED_TO_PROCESS_BLOCKCHAIN_TRANSACTION;
import static com.forink.forink.global.error.ErrorCode.RESUME_NOT_FOUND;
import com.forink.forink.guide.entity.GuideBlockchainAccount;
import com.forink.forink.guide.entity.dao.GuideBlockchainAccountRepository;
import com.forink.forink.guide.util.BlockchainKeyHelper;
import com.forink.forink.guide.util.EthereumAccountCredentials;
import com.forink.forink.guide.util.GuideVerificationManager;
import com.forink.forink.member.entity.Member;
import com.forink.forink.resume.application.dto.response.ResumeResponse;
import com.forink.forink.resume.domain.ResumeStepUpdater;
import com.forink.forink.resume.entity.Resume;
import com.forink.forink.resume.entity.dao.ResumeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;

    private final GuideBlockchainAccountRepository guideBlockchainAccountRepository;

    private final BlockchainKeyHelper blockchainKeyHelper;

    private final GuideVerificationManager guideVerificationManager;

    @Transactional
    public void createResume(final Member member) {
        resumeRepository.save(Resume.builder()
                .member(member)
                .build());
    }

    @Transactional
    public void submitResume(final Long memberId) {
        Resume resume = resumeRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new BusinessException(RESUME_NOT_FOUND));
        Member member = resume.getMember();

        resume.complete();
        member.qualifyAsPreGuide();

        GuideBlockchainAccount account = createAndSaveBlockchainAccount(member);
        registerGuideOnBlockchain(account);
    }

    private GuideBlockchainAccount createAndSaveBlockchainAccount(final Member member) {
        try {
            EthereumAccountCredentials credentials = blockchainKeyHelper.generateCredentials();

            GuideBlockchainAccount account = GuideBlockchainAccount.builder()
                    .member(member)
                    .ethereumAddress(credentials.address())
                    .privateKey(credentials.privateKey())
                    .build();
            return guideBlockchainAccountRepository.save(account);
        } catch (Exception e) {
            throw new BusinessException(FAILED_TO_GENERATE_BLOCKCHAIN_KEY);
        }
    }

    private void registerGuideOnBlockchain(final GuideBlockchainAccount account) {
        try {
            String privateKey = blockchainKeyHelper.decrypt(account.getPrivateKey());
            guideVerificationManager.loadGuideVerification(privateKey)
                    .registerGuide()
                    .send();
        } catch (Exception e) {
            throw new BusinessException(FAILED_TO_PROCESS_BLOCKCHAIN_TRANSACTION);
        }
    }

    public ResumeResponse getResume(final Long memberId) {
        Resume resume = resumeRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new BusinessException(RESUME_NOT_FOUND));
        return ResumeResponse.from(resume);
    }

    @Transactional
    public void updateResumeByStep(final Long memberId,
                                   final Integer stepNumber,
                                   final String answer) {
        Resume resume = resumeRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new BusinessException(RESUME_NOT_FOUND));
        ResumeStepUpdater updater = ResumeStepUpdater.from(stepNumber);
        updater.update(resume, answer);
    }

}
