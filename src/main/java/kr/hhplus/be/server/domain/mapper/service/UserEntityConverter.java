package kr.hhplus.be.server.domain.mapper.service;

import kr.hhplus.be.server.domain.User.User;
import kr.hhplus.be.server.domain.service.dto.UserServiceResponse;

public class UserEntityConverter {
    public static UserServiceResponse ToServiceResponse(User user) {
        return new UserServiceResponse(
                user.getUserId(),
                user.getBalance()
        );
    }
}
