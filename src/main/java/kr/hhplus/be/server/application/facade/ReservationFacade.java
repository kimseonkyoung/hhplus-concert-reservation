package kr.hhplus.be.server.application.facade;

import kr.hhplus.be.server.application.mapper.facade.TokenDtoConverter;
import kr.hhplus.be.server.application.mapper.facade.UserDtoConverter;
import kr.hhplus.be.server.domain.token.TokenService;
import kr.hhplus.be.server.domain.User.UserService;
import kr.hhplus.be.server.domain.service.dto.TokenServiceResponse;
import kr.hhplus.be.server.domain.service.dto.UserServiceRequest;
import kr.hhplus.be.server.domain.service.dto.UserServiceResponse;
import kr.hhplus.be.server.interfaces.api.dto.BalanceRequest;
import kr.hhplus.be.server.interfaces.api.dto.BalanceResponse;
import kr.hhplus.be.server.interfaces.api.dto.TokenResponse;
import org.springframework.stereotype.Service;

@Service
public class ReservationFacade {

    private final UserService userService;
    private final TokenService tokenService;

    public ReservationFacade(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    /**
     * 해당 유저의 잔액을 조회하기 위해 요청을 전달합니다.
     * @param userId
     * @return userId, balance
     * @Author [kimseonkyoung]
     */
    public BalanceResponse getUserBalance(Long userId) {
        // UserService 호출
        UserServiceResponse response = userService.getUserBalance(userId);
        return UserDtoConverter.toControllerResponse(response);
    }

    /**
     * 해당 유저의 잔액을 충전하기 위해 요청을 전달합니다.
     * @param  request
     * @return 유저 충전 결과(UserServiceResponse)
     * @Author [kimseonkyoung]
     */
    public BalanceResponse chargeUserBalance(BalanceRequest request) {
        // Controller DTO -> Service DTO 변환
        UserServiceRequest serviceRequest = UserDtoConverter.toServiceRequest(request);
        // UserService 호출
        UserServiceResponse response = userService.chargeUserBalance(serviceRequest);
        return UserDtoConverter.toControllerResponse(response);
    }

    /**
     * 해당 유저의 토큰을 발급하기 위해 요청을 전달합니다.
     * @param  userId
     * @return 토큰 반환(TokenResponse)
     * @Author [kimseonkyoung]
     */
    public TokenResponse createToken(long userId) {
        // TokenService 호출
        TokenServiceResponse response = tokenService.createToken(userId);
        return TokenDtoConverter.toControllerResponse(response);
    }
}
