package kr.hhplus.be.server.domain.User;

import kr.hhplus.be.server.domain.User.User;
import kr.hhplus.be.server.domain.User.repository.UserRepository;
import kr.hhplus.be.server.domain.exception.UserNotFoundException;
import kr.hhplus.be.server.domain.mapper.service.ReservationEntityConverter;
import kr.hhplus.be.server.domain.mapper.service.UserEntityConverter;
import kr.hhplus.be.server.domain.service.dto.UserServiceRequest;
import kr.hhplus.be.server.domain.service.dto.UserServiceResponse;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserServiceResponse getUserBalance(Long userId) {
        if(userId <= 0) {
            throw new IllegalArgumentException("잘못된 userId 입니다.");
        }
        // 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 유저는 존재하지 않습니다." + userId));

        // Entity -> Service Dto 변환
        return UserEntityConverter.ToServiceResponse(user);
    }

    public UserServiceResponse chargeUserBalance(UserServiceRequest serviceRequest) {
        Long userId = serviceRequest.getUserId();
        int amount = serviceRequest.getAmount();
        // 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 유저는 존재하지 않습니다." + userId));

        // 충전금액을 더하는 비즈니스 로직
        user.chargeBalance(amount);

        // 유저 정보 업데이트
        userRepository.save(user);

        // Entity -> Service Dto 변환
        return UserEntityConverter.ToServiceResponse(user);

    }
}
