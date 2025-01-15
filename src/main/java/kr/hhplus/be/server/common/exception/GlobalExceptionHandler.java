package kr.hhplus.be.server.common.exception;


import jakarta.servlet.http.HttpServletRequest;
import kr.hhplus.be.server.domain.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static kr.hhplus.be.server.common.exception.ErrorCode.*;

@RestControllerAdvice(basePackages = "kr.hhplus.be.server.common.interfaces.api.*")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> HandlerException(Exception e, HttpServletRequest request) {
        return ResponseEntity.status(500).body(new kr.hhplus.be.server.common.exception.ErrorResponse(COMMON_ERROR));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception e) {
        return ResponseEntity.status(400).body(new ErrorResponse(BAD_REQUEST));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> UserNotFoundException(Exception e) {
        return ResponseEntity.status(400).body(new ErrorResponse(MISSING_USER));
    }



}
