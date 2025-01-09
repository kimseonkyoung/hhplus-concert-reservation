package kr.hhplus.be.server.application.facade;

import kr.hhplus.be.server.application.mapper.facade.ConcertDtoConvert;
import kr.hhplus.be.server.application.mapper.facade.TokenDtoConverter;
import kr.hhplus.be.server.application.mapper.facade.UserDtoConverter;
import kr.hhplus.be.server.domain.concert.ConcertService;
import kr.hhplus.be.server.domain.service.dto.*;
import kr.hhplus.be.server.domain.token.TokenService;
import kr.hhplus.be.server.domain.User.UserService;
import kr.hhplus.be.server.interfaces.api.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationFacade {

    private final UserService userService;
    private final TokenService tokenService;
    private final ConcertService concertService;

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

    /**
     * 해당 유저가 예약할 수 있는 콘서트 목록을 조회하기 위한 요청을 전달합니다.
     * @return 콘서트 목록 반환(ConcertList)
     * @Author [kimseonkyoung]
     */
    public ConcertResponse getConcertList(int offset, int limit) {
        ConcertServiceResponse serviceResponse = concertService.getConcertList(offset, limit);
        ConcertResponse concertResponse = ConcertDtoConvert.toControllerConcertListResponse(serviceResponse);
        return concertResponse;
    }

    /**
     * 해당 유저가 선택한 콘서트가 열리는 날짜 목록을 조회하기 위한 요청을 전달합니다.
     * @return 콘서트 날짜 목록 반환(ConcertList)
     * @Author [kimseonkyoung]
     */
    public ConcertDatesResponse getConcertDates(Long concertId, int offset, int limit) {
        ConcertServiceDatesResponse concertDateList = concertService.getConcertDateList(concertId, offset, limit);
        ConcertDatesResponse response = ConcertDtoConvert.tocontrollerConcertDateListResponse(concertDateList);
        return response;
    }

    /**
     * 해당 유저가 선택한 날짜의 예약 가능한 날짜가 조회됩니다.
     * @return 콘서트 좌석 목록 반환(SeatList)
     * @Author [kimseonkyoung]
     */
    public List<SeatResponse> getSeatsForDate(Long concertScheduleId) {
        List<SeatServiceResponse> seatResponse = concertService.getSeatsForDate(concertScheduleId);
        List<SeatResponse> response = ConcertDtoConvert.tocontrollerConcertSeatListResponse(seatResponse);
        return response;
    }
}
