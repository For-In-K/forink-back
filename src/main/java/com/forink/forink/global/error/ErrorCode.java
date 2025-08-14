package com.forink.forink.global.error;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // OAuth
    OAUTH_AUTHORIZATION_FAILED(HttpStatus.UNAUTHORIZED, "소셜 로그인 인증에 실패했습니다."),
    FAILED_TO_GET_ACCESS_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "액세스 토큰을 가져오는 데 실패했습니다."),
    FAILED_TO_GET_OAUTH_USERINFO(HttpStatus.INTERNAL_SERVER_ERROR, "소셜 로그인 사용자 정보를 가져오는 데 실패했습니다."),
    OAUTH_NETWORK_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "소셜 로그인 과정 중 네트워크 오류가 발생했습니다."),
    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다."),
    ONLY_REGULAR_MEMBER_ELIGIBLE_FOR_PREGUIDE(HttpStatus.FORBIDDEN, "오직 일반 회원만 예비 가이드 자격을 얻을 수 있습니다."),
    // Exam
    EXAM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 진단 검사 항목을 찾을 수 없습니다."),
    // Resume
    RESUME_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이력서를 찾을 수 없습니다."),
    REQUIRED_ANSWERS_NOT_COMPLETED(HttpStatus.BAD_REQUEST, "모든 필수 답변이 작성되지 않아 이력서를 완료할 수 없습니다."),
    RESUME_STEP_INACCESSIBLE(HttpStatus.BAD_REQUEST, "접근할 수 없는 이력서 페이지입니다."),
    // Roadmap
    ROADMAP_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 로드맵을 찾을 수 없습니다."),
    ROADMAP_STEP_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 로드맵 스텝을 찾을 수 없습니다."),
    ROADMAP_STEP_CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 로드맵 체크리스트를 찾을 수 없습니다."),
    ROADMAP_ACCESS_DENIED(HttpStatus.FORBIDDEN, "다른 회원의 로드맵에 접근할 수 없습니다."),
    ROADMAP_FEEDBACK_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 로드맵 피드백을 찾을 수 없습니다."),
    // Chat
    CHAT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 챗봇을 찾을 수 없습니다."),
    // AI
    FAILED_TO_GET_CHAT_ANSWER(HttpStatus.INTERNAL_SERVER_ERROR, "AI 챗봇 답변을 가져오는 데 실패했습니다."),
    FAILED_TO_GENERATE_ROADMAP(HttpStatus.INTERNAL_SERVER_ERROR, "AI 로드맵 생성에 실패했습니다."),
    AI_NETWORK_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "AI 서버와 통신 중 네트워크 오류가 발생했습니다."),
    // Blockchain
    FAILED_TO_GENERATE_BLOCKCHAIN_KEY(HttpStatus.INTERNAL_SERVER_ERROR, "블록체인 키 생성에 실패했습니다."),
    FAILED_TO_PROCESS_BLOCKCHAIN_TRANSACTION(HttpStatus.INTERNAL_SERVER_ERROR, "블록체인 트랜잭션 처리에 실패했습니다."),
    ;

    private final HttpStatus httpStatus;

    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}