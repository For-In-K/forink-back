package com.forink.forink.resume.domain;

import com.forink.forink.global.error.BusinessException;
import static com.forink.forink.global.error.ErrorCode.RESUME_STEP_INACCESSIBLE;
import com.forink.forink.resume.entity.Resume;
import java.util.Arrays;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ResumeStepUpdater {

    NAME(1) {
        public void update(Resume resume, String answer) { resume.updateAnswerName(answer); }
    },
    AGE(2) {
        public void update(Resume resume, String answer) { resume.updateAnswerAge(answer); }
    },
    NATIONALITY(3) {
        public void update(Resume resume, String answer) { resume.updateAnswerNationality(answer); }
    },
    LANGUAGE(4) {
        public void update(Resume resume, String answer) { resume.updateAnswerLanguage(answer); }
    },
    EXPERTISE(5) {
        public void update(Resume resume, String answer) { resume.updateAnswerExpertise(answer); }
    },
    LINK(6) {
        public void update(Resume resume, String answer) { resume.updateAnswerLink(answer); }
    };

    private final Integer stepNumber;

    public abstract void update(Resume resume, String answer);

    public static ResumeStepUpdater from(Integer stepNumber) {
        return Arrays.stream(values())
                .filter(updater -> Objects.equals(updater.stepNumber, stepNumber))
                .findFirst()
                .orElseThrow(() -> new BusinessException(RESUME_STEP_INACCESSIBLE));
    }

}


