package kr.hhplus.be.server.domain.common.exception;

import kr.hhplus.be.server.common.globalErrorHandler.ErrorResponse;

public class ConcurrentOperationException extends RuntimeException {
    public ConcurrentOperationException(ErrorResponse message) {
        super(String.valueOf(message));
    }
}
