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
    // Exam

    // Resume

    // Roadmap
    ;

    private final HttpStatus httpStatus;

    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
