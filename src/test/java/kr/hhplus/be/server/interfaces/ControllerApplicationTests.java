package kr.hhplus.be.server.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.hhplus.be.server.application.facade.ReservationFacade;
import kr.hhplus.be.server.application.mapper.facade.ConcertDtoConvert;
import kr.hhplus.be.server.domain.concert.Concert;
import kr.hhplus.be.server.domain.concert.ConcertSchedule;
import kr.hhplus.be.server.domain.mapper.service.ConcertEntityConverter;
import kr.hhplus.be.server.domain.concert.Seat;
import kr.hhplus.be.server.domain.concert.SeatStatus;
import kr.hhplus.be.server.domain.service.dto.ConcertServiceDatesResponse;
import kr.hhplus.be.server.domain.service.dto.ConcertServiceResponse;
import kr.hhplus.be.server.domain.service.dto.SeatServiceResponse;
import kr.hhplus.be.server.domain.token.TokenStatus;
import kr.hhplus.be.server.infrastructure.interceptor.TokenInterceptor;
import kr.hhplus.be.server.interfaces.api.controller.ConcertController;
import kr.hhplus.be.server.interfaces.api.controller.TokenController;
import kr.hhplus.be.server.interfaces.api.controller.UserController;
import kr.hhplus.be.server.interfaces.api.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest({TokenController.class, UserController.class, ConcertController.class})
@AutoConfigureMockMvc
public class ControllerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservationFacade facade;

    @MockitoBean
    private TokenInterceptor mockInterceptor;

    @Autowired
    private ObjectMapper objectMapper; // JSON 변환을 위한 ObjectMapper

    @BeforeEach
    public void setUp() throws Exception {
        // 모든 요청을 허용하도록 Mock 설정
        given(mockInterceptor.preHandle(
                any(HttpServletRequest.class),
                any(HttpServletResponse.class),
                any(Object.class)
        )).willReturn(true);
    }

    @Test
    @DisplayName("유저의 잔액 조회: 성공")
    void test1() throws Exception {
        //given
        BalanceResponse response = new BalanceResponse(1L,1000);

        //when
        given(facade.getUserBalance(1L)).willReturn(response);

        //then
        mockMvc.perform(get("/api/user/balance/{userId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.balance").value(1000))
                .andDo(print());
    }

    @Test
    @DisplayName("유저의 잔액 조회: 실패(userId가 음수인 경우)")
    void test2() throws Exception {
        // given
        given(facade.getUserBalance(-1L)).willThrow(new IllegalArgumentException("잘못된 userId 입니다."));

        // then
        mockMvc.perform(get("/api/user/balance/{userId}", -1L))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("유저의 잔액 충전: 성공")
    void test3() throws Exception {
        // given
        BalanceRequest request = new BalanceRequest(1L, 100);
        BalanceResponse response = new BalanceResponse(1L, 1500, 100);

        // when
        given(facade.chargeUserBalance(any(BalanceRequest.class))).willReturn(response);

        // then
        mockMvc.perform(post("/api/user/balance/charge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))) // 수정된 부분
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.balance").value(1500))
                .andExpect(jsonPath("$.amount").value(100))
                .andDo(print());
    }

    @DisplayName("처음 진입한 유저 토큰 생성 테스트")
    @Test
    void test5 () throws Exception {
        // given
        TokenResponse response = new TokenResponse("6asd-65465fasd4f-5465", 500, TokenStatus.WAIT,
                LocalDateTime.of(2025, 1, 6, 0, 0));

        // when
        given(facade.createToken(1L)).willReturn(response);

        // then
        mockMvc.perform(get("/api/token/create")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tokenUuid").value("6asd-65465fasd4f-5465"))
                .andExpect(jsonPath("$.position").value(500))
                .andExpect(jsonPath("$.status").value("WAIT"))
                .andDo(print());
    }

    @DisplayName("콘서트 목록조회 테스트")
    @Test
    void test6 () throws Exception {
        // Mocked Concert 엔티티 리스트
        List<Concert> concerts = Arrays.asList(
                Concert.create(1L, "김연자의 콘서트"),
                Concert.create(2L, "나훈아의 콘서트")
        );

        // Entity -> Service Dto
        ConcertServiceResponse serviceResponse = ConcertEntityConverter.toServiceResponse(concerts, 5, 0, 10);

        // Service Dto -> Controller Dto
        ConcertResponse concertResponse = ConcertDtoConvert.toControllerConcertListResponse(serviceResponse);

        // Service Mock 설정
        given(facade.getConcertList(0, 10)).willReturn(concertResponse);

        // Perform GET request and assert the response
        mockMvc.perform(get("/api/concerts/list?offset=0&limit=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCount").value(5))
                .andExpect(jsonPath("$.offset").value(0))
                .andExpect(jsonPath("$.limit").value(10))
                .andExpect(jsonPath("$.concerts.length()").value(2))
                .andExpect(jsonPath("$.concerts[0].id").value(1))
                .andExpect(jsonPath("$.concerts[0].title").value("김연자의 콘서트"))
                .andExpect(jsonPath("$.concerts[1].id").value(2))
                .andExpect(jsonPath("$.concerts[1].title").value("나훈아의 콘서트"));
    }

    @DisplayName("콘서트 날짜조회 테스트")
    @Test
    void test7 () throws Exception {
        List<ConcertSchedule> concertSchedules = Arrays.asList(
                ConcertSchedule.create(1L, LocalDateTime.of(2025, 1, 9, 11, 0, 0)),
                ConcertSchedule.create(1L, LocalDateTime.of(2025, 1, 10, 11, 0, 0))
        );

        // Entity -> Service Dto
        ConcertServiceDatesResponse serviceResponse = ConcertEntityConverter.toServiceDatesResponse(concertSchedules, 5, 0, 10);

        // Service Dto -> Controller Dto
        ConcertDatesResponse dateResponse = ConcertDtoConvert.tocontrollerConcertDateListResponse(serviceResponse);

        // Service Mock 설정
        given(facade.getConcertDates(1L, 0, 10)).willReturn(dateResponse);

        // Perform GET request and assert the response
        mockMvc.perform(get("/api/concerts/availableDates?concertId=1&offset=0&limit=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCount").value(5))
                .andExpect(jsonPath("$.offset").value(0))
                .andExpect(jsonPath("$.limit").value(10))
                .andExpect(jsonPath("$.dates.length()").value(2))
                .andExpect(jsonPath("$.dates[0].scheduleId").value(1))
                .andExpect(jsonPath("$.dates[0].dates").value("2025-01-09T11:00:00"))
                .andExpect(jsonPath("$.dates[1].scheduleId").value(1))
                .andExpect(jsonPath("$.dates[1].dates").value("2025-01-10T11:00:00"));
    }

    @DisplayName("콘서트 좌석 조회 테스트")
    @Test
    void test8 () throws Exception {
        List<Seat> seats = Arrays.asList(
                Seat.create(1L, 1L, SeatStatus.INACTIVE),
                Seat.create(2L, 1L, SeatStatus.INACTIVE),
                Seat.create(3L, 1L, SeatStatus.INACTIVE),
                Seat.create(4L, 1L, SeatStatus.ACTIVE),
                Seat.create(5L, 1L, SeatStatus.ACTIVE),
                Seat.create(6L, 1L, SeatStatus.ACTIVE),
                Seat.create(7L, 1L, SeatStatus.ACTIVE)
        );

        // Entity -> Service Dto
        List<SeatServiceResponse> serviceResponse = ConcertEntityConverter.toServiceSeatsResponse(seats);

        // Service Dto -> Controller Dto
        List<SeatResponse> dateResponse = ConcertDtoConvert.tocontrollerConcertSeatListResponse(serviceResponse);

        // Service Mock 설정
        given(facade.getSeatsForDate(1L)).willReturn(dateResponse);

        // Perform GET request and assert the response
        mockMvc.perform(get("/api/concerts/seats?concertScheduleId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].seatId").value(1L))
                .andExpect(jsonPath("$.[0].concertScheduleId").value(1L))
                .andExpect(jsonPath("$.[0].status").value("INACTIVE"))
                .andExpect(jsonPath("$.[3].seatId").value(4L))
                .andExpect(jsonPath("$.[3].concertScheduleId").value(1L))
                .andExpect(jsonPath("$.[3].status").value("ACTIVE"));
    }
}
