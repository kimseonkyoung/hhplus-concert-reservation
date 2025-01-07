package kr.hhplus.be.server.common.exception;

public class ErrorResponse {
    private Integer code;
    private String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getStatusCode(); // Enum 이름을 코드로 사용
        this.message = errorCode.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}