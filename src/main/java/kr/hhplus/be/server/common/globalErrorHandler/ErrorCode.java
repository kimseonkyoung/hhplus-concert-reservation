package kr.hhplus.be.server.common.globalErrorHandler;

import jakarta.servlet.http.HttpServletResponse;

public enum ErrorCode {
    COMMON_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"서버 에러입니다."),
    MISSING_TOKEN(HttpServletResponse.SC_BAD_REQUEST, "헤더에 UUID 토큰이 존재하지 않습니다."),
    INVALID_TOKEN_FORMAT(HttpServletResponse.SC_BAD_REQUEST,"대기열 UUID 토큰의 형식이 올바르지 않습니다."),
    TOKEN_NOT_FOUND(HttpServletResponse.SC_BAD_REQUEST,"유저의 해당 토큰이 대기열에 존재하지 않습니다."),
    INVALID_TOKEN_STATUS(HttpServletResponse.SC_BAD_REQUEST, "해당 유저의 토큰 상태가 만료되었습니다."),
    BAD_REQUEST(HttpServletResponse.SC_BAD_REQUEST, "잘못된 요청입니다."),
    MISSING_USER(HttpServletResponse.SC_NOT_FOUND,"해당 유저를 찾을 수 없습니다."),
    MISSING_RESERVATION(HttpServletResponse.SC_BAD_REQUEST, "해당 예약정보를 찾을 수 없습니다."),
    INSUFFICIENT_BALANCE(HttpServletResponse.SC_BAD_REQUEST, "현재 잔액이 충분하지 않습니다.");

    private final int statusCode;
    private final String message;

    ErrorCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public int getStatusCode() {
        return statusCode;
    }
}

