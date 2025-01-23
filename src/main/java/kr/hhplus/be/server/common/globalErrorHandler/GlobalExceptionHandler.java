package kr.hhplus.be.server.common.globalErrorHandler;


import jakarta.servlet.http.HttpServletRequest;
import kr.hhplus.be.server.domain.common.exception.ConcurrentOperationException;
import kr.hhplus.be.server.domain.common.exception.InsufficientBalanceException;
import kr.hhplus.be.server.domain.common.exception.ReservationNotFoundException;
import kr.hhplus.be.server.domain.common.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static kr.hhplus.be.server.common.globalErrorHandler.ErrorCode.*;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerException(Exception e, HttpServletRequest request) {
        return ResponseEntity.status(500).body(new kr.hhplus.be.server.common.globalErrorHandler.ErrorResponse(COMMON_ERROR));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception e) {
        return ResponseEntity.status(400).body(new ErrorResponse(BAD_REQUEST));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFoundException(Exception e) {
        return ResponseEntity.status(400).body(new ErrorResponse(MISSING_USER));
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ErrorResponse reservationNotFoundException() {
        return new ErrorResponse(MISSING_RESERVATION);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> InsufficientBalanceException(Exception e) {
        return ResponseEntity.status(400).body(new ErrorResponse(INSUFFICIENT_BALANCE));
    }

    @ExceptionHandler(ConcurrentOperationException.class)
    public ResponseEntity<ErrorResponse> ConcurrentOperationException(Exception e) {
        return ResponseEntity.status(400).body(new ErrorResponse(INSUFFICIENT_BALANCE));
    }


}


