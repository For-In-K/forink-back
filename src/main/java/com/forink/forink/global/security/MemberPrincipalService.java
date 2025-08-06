package com.forink.forink.global.security;

import com.forink.forink.global.error.BusinessException;
import static com.forink.forink.global.error.ErrorCode.MEMBER_NOT_FOUND;
import com.forink.forink.global.security.data.MemberPrincipal;
import com.forink.forink.member.entity.Member;
import com.forink.forink.member.entity.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberPrincipalService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Long memberId = Long.parseLong(userId);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));

        return new MemberPrincipal(
                member.getId(),
                member.getName(),
                member.getMemberRoleType().name()
        );
    }

}
