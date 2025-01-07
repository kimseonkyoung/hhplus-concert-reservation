package kr.hhplus.be.server.infrastructure.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.hhplus.be.server.application.mapper.facade.TokenDtoConverter;
import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.common.exception.ErrorResponse;
import kr.hhplus.be.server.domain.service.dto.TokenServiceResponse;
import kr.hhplus.be.server.domain.token.TokenService;
import kr.hhplus.be.server.interfaces.api.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;
    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler) throws  Exception {
        String tokenUuid = request.getHeader("x-token");

        // 1. x-token 헤더: uuid 토큰 존재 유무 확인
        if (tokenUuid == null || tokenUuid.isEmpty()) {
            sendErrorResponse(response, ErrorCode.MISSING_TOKEN);
            return false;
        }

        // 2. x-token 형식 검증
        if (!TokenValidator.isValidUuid(tokenUuid)) {
            sendErrorResponse(response, ErrorCode.INVALID_TOKEN_FORMAT);
            return false;
        }

        try {
            // 3. DB 조회(순번, 상태)
            TokenServiceResponse tokenInfo = tokenService.getTokenStatusAndPosition(tokenUuid);
            if(tokenInfo == null) {
                sendErrorResponse(response, ErrorCode.MISSING_USER);
            }
            if("ACTIVE".equalsIgnoreCase(String.valueOf(tokenInfo.getStatus()))) {
                // 4. 상태가 "ACTIVE"이면
                sendSuccessResponse(response, TokenDtoConverter.toControllerResponse(tokenInfo));
            } else if("WAIT".equalsIgnoreCase(String.valueOf(tokenInfo.getStatus()))) {
                // 5. 상태가 "WAIT"이면
                sendSuccessResponse(response, TokenDtoConverter.toControllerResponse(tokenInfo));
            } else {
                // 6. 상태가 "EXPIRED"이면
                sendErrorResponse(response, ErrorCode.INVALID_TOKEN_STATUS);
            }
            return false;

        } catch (RuntimeException e) {
            log.error("RuntimeException 발생.");
            sendErrorResponse(response, ErrorCode.COMMON_ERROR);
            return false;
        }

    }
    private void sendSuccessResponse(HttpServletResponse response, TokenResponse tokenResponse) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(tokenResponse));
    }

    private void sendErrorResponse(HttpServletResponse response, ErrorCode code) throws IOException {
        response.setStatus(code.getStatusCode());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new ErrorResponse(code)));
    }
}
