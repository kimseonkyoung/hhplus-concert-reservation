package kr.hhplus.be.server.domain.common.exception;

import kr.hhplus.be.server.common.globalErrorHandler.ErrorResponse;

public class SeatProgressException extends RuntimeException {
    public SeatProgressException(ErrorResponse message) {
        super(String.valueOf(message));
    }
}
