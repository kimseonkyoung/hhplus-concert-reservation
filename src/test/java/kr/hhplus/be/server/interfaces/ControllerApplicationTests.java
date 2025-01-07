package kr.hhplus.be.server.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.application.facade.ReservationFacade;
import kr.hhplus.be.server.domain.token.TokenStatus;
import kr.hhplus.be.server.interfaces.api.controller.TokenController;
import kr.hhplus.be.server.interfaces.api.controller.UserController;
import kr.hhplus.be.server.interfaces.api.dto.BalanceRequest;
import kr.hhplus.be.server.interfaces.api.dto.BalanceResponse;
import kr.hhplus.be.server.interfaces.api.dto.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest({TokenController.class, UserController.class})
@AutoConfigureMockMvc
public class ControllerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservationFacade facade;

    @Autowired
    private ObjectMapper objectMapper; // JSON 변환을 위한 ObjectMapper

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
}
