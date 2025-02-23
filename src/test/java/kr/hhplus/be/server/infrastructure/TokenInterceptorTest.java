package kr.hhplus.be.server.infrastructure;

import kr.hhplus.be.server.application.usecase.ConcertReservationFacade ;
import kr.hhplus.be.server.common.globalErrorHandler.ErrorCode;
import kr.hhplus.be.server.domain.common.dto.TokenServiceResponse;
import kr.hhplus.be.server.domain.token.service.TokenService;
import kr.hhplus.be.server.domain.token.TokenStatus;
import kr.hhplus.be.server.common.interceptor.TokenInterceptor;
import kr.hhplus.be.server.interfaces.api.controller.ConcertController;
import kr.hhplus.be.server.interfaces.api.controller.ReservationController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;


@WebMvcTest({ReservationController.class, ConcertController.class})
@Import(TokenInterceptor.class)
public class TokenInterceptorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private ConcertReservationFacade facade;

    @BeforeEach
    void setup() {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMinutes(5);

        // "WAIT" 상태의 응답 설정
       given(tokenService.getTokenPositionWithStatus("123e4567-e89b-12d3-a456-426614174000"))
                .willReturn(new TokenServiceResponse("123e4567-e89b-12d3-a456-426614174000", 3, TokenStatus.WAIT, createdAt));

        // "ACTIVE" 상태의 응답 설정
        given(tokenService.getTokenPositionWithStatus("123e4567-e89b-12d3-a456-426614174001"))
                .willReturn(new TokenServiceResponse("123e4567-e89b-12d3-a456-426614174001", null, TokenStatus.ACTIVE, createdAt));

    }

    @DisplayName("헤더가 없을 시 에러 반환")
    @Test
    void test1() throws Exception {
        mockMvc.perform(get("/api/"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.MISSING_TOKEN.getStatusCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.MISSING_TOKEN.getMessage()));
    }

    @DisplayName("유저가 폴링시 헤더에 UUID 토큰이 없을 시 에러 반환")
    @Test
    void test2() throws Exception {
        mockMvc.perform(get("/api/"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.MISSING_TOKEN.getStatusCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.MISSING_TOKEN.getMessage()));
    }

    @DisplayName("유저가 폴링시 잘못된 UUID 형식일 시 에러 반환")
    @Test
    void test3() throws Exception {
        mockMvc.perform(get("/api/").header("x-token", "invalid-uuid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.INVALID_TOKEN_FORMAT.getStatusCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_TOKEN_FORMAT.getMessage()));
    }

    @DisplayName("유저가 폴링시 토큰 UUID Status가 'WAIT'일 시 순번 반환")
    @Test
    void test4() throws Exception {
        mockMvc.perform(get("/api/").header("x-token", "123e4567-e89b-12d3-a456-426614174000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("WAIT"))
                .andExpect(jsonPath("$.position").value(3));
    }

    @DisplayName("유저가 폴링시 토큰 UUID Status가 'ACTIVE'일 시 좌석 예약 진입")
    @Test
    void test5() throws Exception {
        mockMvc.perform(get("/api/")
                        .header("x-token", "123e4567-e89b-12d3-a456-426614174001"))
                .andExpect(status().isOk()) // 상태 코드 확인
                .andExpect(jsonPath("$.status").value("ACTIVE")) // 상태 확인
                .andExpect(jsonPath("$.position").doesNotExist()); // position이 없어야 함
    }

    @DisplayName("유저가 폴링시 토큰 UUID Status가 'EXPIRED'일 시 에러 반환")
    @Test
    void test6() throws Exception{
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMinutes(5);

        given(tokenService.getTokenPositionWithStatus("123e4567-e89b-12d3-a456-426614174002"))
                .willReturn(new TokenServiceResponse("123e4567-e89b-12d3-a456-426614174002", null, TokenStatus.EXPIRED, createdAt));

        mockMvc.perform(get("/api/").header("x-token", "123e4567-e89b-12d3-a456-426614174002"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.EXPIRED_TOKEN_STATUS.getStatusCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.EXPIRED_TOKEN_STATUS.getMessage()));
    }

    @Test
    void test7() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/")
                        .header("x-token", "123e4567-e89b-12d3-a456-426614174001"))
                .andReturn();

        // 응답 상태 코드 출력
        System.out.println("Response Status: " + result.getResponse().getStatus());

        // 응답 본문 출력
        System.out.println("Response Body: " + result.getResponse().getContentAsString());
    }
}
