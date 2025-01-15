package kr.hhplus.be.server.domain.Token;

import kr.hhplus.be.server.domain.common.dto.TokenServiceResponse;
import kr.hhplus.be.server.domain.token.Token;
import kr.hhplus.be.server.domain.token.TokenGenerator;
import kr.hhplus.be.server.domain.token.service.TokenService;
import kr.hhplus.be.server.domain.token.TokenStatus;
import kr.hhplus.be.server.domain.token.repository.TokenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

	@Mock
	private TokenRepository tokenRepository;

	@Mock
	private TokenGenerator tokenGenerator;

	@InjectMocks
	private TokenService tokenService; // 의존성 주입 자동 처리

	@Test
	@DisplayName("해당 유저의 토큰 uuid가 없으면 새로 발급: 성공")
	void test1 () {
		LocalDateTime createdAt = LocalDateTime.now();
		LocalDateTime expiredAt = createdAt.plusMinutes(5);

		// Given
		Token newToken = Token.createWait("uuid123", createdAt);
		given(tokenGenerator.createToken(51)).willReturn(newToken);
		doNothing().when(tokenRepository).save(newToken);

		// When
		TokenServiceResponse response = tokenService.createToken(1L);
		response.setPosition(5);

		// Then
		verify(tokenGenerator, times(1)).createToken(1);
		verify(tokenRepository, times(1)).save(newToken);
		verify(tokenRepository, never()).updateTokenStatus(anyString(), any());
		assertEquals("uuid123", response.getTokenUuid());
		assertEquals(5, response.getPosition());
	}

	@Test
	@DisplayName("해당 유저의 토큰이 존재할시 예전 토큰을 만료하는지 테스트")
	void test2() {
		LocalDateTime createdAt = LocalDateTime.now();
		LocalDateTime expiredAt = createdAt.plusMinutes(5);
		// Given
		long userId = 123L;
		Token oldToken = Token.createWait("uuid123", createdAt);
		Token newToken = Token.createActive("uuid456", createdAt);

		given(tokenRepository.findById(userId)).willReturn(Optional.of(oldToken));
		given(tokenGenerator.createToken(1)).willReturn(newToken);

		// When
		tokenService.createToken(userId);

		// Then
		verify(tokenRepository, times(1)).updateTokenStatus("uuid123", TokenStatus.EXPIRED);
		verify(tokenRepository, times(1)).save(newToken);
	}

	@Test
	@DisplayName("해당 유저의 토큰 uuid 연속을 두 번 발급시 두 번째 uuid를 유저에게 전달")
	void test3() {
		LocalDateTime createdAt = LocalDateTime.now();
		LocalDateTime expiredAt = createdAt.plusMinutes(5);
		// Given
		long userId = 123L;
		Token oldToken = Token.createWait("uuid123", createdAt);
		Token newToken = Token.createWait("uuid123567", createdAt);

		given(tokenRepository.findById(userId)).willReturn(Optional.of(oldToken));
		given(tokenGenerator.createToken(1)).willReturn(newToken);
		doNothing().when(tokenRepository).updateTokenStatus("uuid123", TokenStatus.EXPIRED);
		doNothing().when(tokenRepository).save(newToken);

		// When
		TokenServiceResponse response = tokenService.createToken(userId);

		// Then
		verify(tokenRepository, times(1)).updateTokenStatus("uuid123", TokenStatus.EXPIRED);
		verify(tokenGenerator, times(1)).createToken(1);
		verify(tokenRepository, times(1)).save(newToken);
		assertEquals("uuid123567", response.getTokenUuid());
		assertEquals(TokenStatus.WAIT, response.getStatus());
	}

	@Test
	@DisplayName("유저 30명이 토큰 발급 요청시 30번 토큰 발급 테스트")
	void test5() throws InterruptedException {
		LocalDateTime createdAt = LocalDateTime.now();
		// given
		int userRequest = 30;
		ExecutorService exeCutorService = Executors.newFixedThreadPool(userRequest);
		CountDownLatch latch = new CountDownLatch(userRequest);

		given(tokenGenerator.createToken(20)).willAnswer(invocation ->
				Token.createWait(UUID.randomUUID().toString(), createdAt));

		// when
		for (int i=0; i<userRequest; i++) {
			long userId = i;
			exeCutorService.submit(() -> {
				try {
					TokenServiceResponse response = tokenService.createToken(userId);
					System.out.println("response.setTokenUuid(); = " + response.getTokenUuid());
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		exeCutorService.shutdown();

		// then
		verify(tokenGenerator, times(userRequest)).createToken(20);
		verify(tokenRepository, times(userRequest)).save(any(Token.class));
	}
}
