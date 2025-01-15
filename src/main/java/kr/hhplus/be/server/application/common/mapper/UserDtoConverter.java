package kr.hhplus.be.server.application.common.mapper;

import kr.hhplus.be.server.domain.common.dto.UserServiceRequest;
import kr.hhplus.be.server.domain.common.dto.UserServiceResponse;
import kr.hhplus.be.server.interfaces.api.dto.BalanceRequest;
import kr.hhplus.be.server.interfaces.api.dto.BalanceResponse;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public UserDtoConverter(){

    }

    public static UserServiceRequest toServiceRequest(BalanceRequest request) {
        return new UserServiceRequest(
                request.getUserId(),
                request.getAmount()
        );
    }

    public static BalanceResponse toControllerResponse(UserServiceResponse response) {
        return new BalanceResponse(
                response.getUserId(),
                response.getAmount()
        );
    }
}
