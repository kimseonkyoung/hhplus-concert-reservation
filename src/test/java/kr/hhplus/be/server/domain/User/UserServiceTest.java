package kr.hhplus.be.server.domain.User;

import kr.hhplus.be.server.domain.User.repository.UserRepository;
import kr.hhplus.be.server.domain.exception.UserNotFoundException;
import kr.hhplus.be.server.domain.service.dto.UserServiceRequest;
import kr.hhplus.be.server.domain.service.dto.UserServiceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository; // Mock 객체 생성

	@InjectMocks
	private UserService userService; // 의존성 주입 자동 처리

	@Test
	@DisplayName("해당 유저의 잔액을 조회하는 테스트: 성공")
	void test1() {
		// given
		User user = User.create(1L, 1000);
		given(userRepository.findById(1L)).willReturn(Optional.of(user));

		// when
		UserServiceResponse response = userService.getUserBalance(1L);

		// then
		assertEquals(1L, response.getUserId());
		assertEquals(1000, response.getBalance());
	}

	@Test
	@DisplayName("해당 유저로 잔액을 조회할 때, 유저의 아이디가 존재하지 않으면 UserNotFoundException 예외 발생!")
	void test2() {
		// given
		given(userRepository.findById(1L)).willReturn(Optional.empty());

		// when & then
		UserNotFoundException exception = assertThrows(UserNotFoundException.class,
				() -> userService.getUserBalance(1L));

		assertEquals("해당 유저는 존재하지 않습니다." + 1L, exception.getMessage());
	}

	@Test
	@DisplayName("해당 유저로 잔액을 조회할 때, 유저의 아이디가 음수이면 IllegalArgumentException 예외 발생!")
	void test3() {
		//when & then
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> userService.getUserBalance(-1L));

		assertEquals("잘못된 userId 입니다.", exception.getMessage());
	}

	@Test
	@DisplayName("헤딩 유저의 잔액을 충전할 떄, 유저의 아이디와 충전액이 0원 보다 큰 수 이면 성공!")
	void test4() {
		// given
		User user = User.create(1L, 1000);
		UserServiceRequest request = new UserServiceRequest(1L, 500);
		given(userRepository.findById(request.getUserId())).willReturn(Optional.of(user));
		given(userRepository.save(user)).willReturn(Optional.of(user));

		// when
		UserServiceResponse response = userService.chargeUserBalance(request);

		// then
		assertEquals(1L, response.getUserId());
		assertEquals(1500, response.getBalance());
	}

}
