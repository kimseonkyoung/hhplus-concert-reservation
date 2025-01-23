package kr.hhplus.be.server.application.usecase;

import kr.hhplus.be.server.application.common.mapper.*;
import kr.hhplus.be.server.domain.common.dto.*;
import kr.hhplus.be.server.domain.concert.service.ConcertService;
import kr.hhplus.be.server.domain.reservation.service.PaymentService;
import kr.hhplus.be.server.domain.reservation.service.ReservationService;
import kr.hhplus.be.server.domain.reservation.service.ReservationTestService;
import kr.hhplus.be.server.domain.token.service.TokenService;
import kr.hhplus.be.server.domain.User.service.UserService;
import kr.hhplus.be.server.interfaces.api.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertReservationFacade  {

    private final UserService userService;
    private final TokenService tokenService;
    private final ConcertService concertService;
    private final ReservationService reservationService;
    private final PaymentService paymentService;
    private final ReservationTestService reservationTestService;

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

    /**
     * 해당 유저가 선택한 좌석을 예약 요청합니다.
     * @return 콘서트 좌석 예약 결과 반환(ReservationResponse)
     * @Author [kimseonkyoung]
     */
    @Transactional
    public ReservationResponse reserveSeat(ReservationRequest request, String tokenUuid) {
        // 1. controller request dto -> service dto 변환
        ReservationSearviceRequest reservationRequest = ReservationDtoConverter.toServiceReservationRequest(request);

        // 2. reservation service 호출
        ReservationServiceResponse reservationResponse = reservationService.reserveSeat(reservationRequest);

        // 3. 좌석 상태 예약중 변경 service 호출
        concertService.updateSeatProgress(reservationResponse.getSeatId());

        // 4. token service 호출
        tokenService.setExpiredTimeToken(tokenUuid, reservationResponse.getExpiredAt());

        // 4.  service dto -> reservation response dto 반환
        ReservationResponse response = ReservationDtoConverter.toControllerReservationResponse(reservationResponse);
        return response;
    }

    /**
     * 해당 유저가 선택한 좌석을 결제합니다.
     * @return 결제 결과 반환(SeatList)
     * @Author [kimseonkyoung]
     */
    public PaymentResponse paymentSeat(Long reservationId, String tokenUuid) {
        // 1. 해당 reservation id로 예약 정보 조회.
        ReservationServiceResponse reservationServiceResponse = reservationService.findById(reservationId);

        // 2. 해당 예약 정보를 토대로, seat id 추출 후 좌석 가격 조회
        SeatServiceResponse seatServiceResponse = concertService.getSeatInfo(reservationServiceResponse.getSeatId());

        // 3. 해당 예약 정보를 토대로, user id 추출 후 유저 잔액 조회
        userService.deductBalance(reservationServiceResponse.getUserId(), seatServiceResponse.getPrice());

        // 5. 결제정보 저장
        PaymentServiceResponse serviceResponse = paymentService.paymentSave(reservationServiceResponse.getReservationId());

        // 6. 예약 확정
        reservationService.confirmedReservation(reservationId);

        // 7. 좌석 상태 완료
        concertService.updateSeatCompleted(reservationServiceResponse.getSeatId());

        // 8. 토큰 만료
        tokenService.expireTokenOnCompleted(tokenUuid);

        // 9. Service dto -> controller dto 변환
        PaymentResponse controllerResponse = PaymentDtoConvert.toControllerPaymentResponse(serviceResponse);
        return controllerResponse;
    }
}