package kr.hhplus.be.server.application;

import kr.hhplus.be.server.application.common.mapper.UserDtoConverter;
import kr.hhplus.be.server.domain.common.dto.UserServiceRequest;
import kr.hhplus.be.server.interfaces.api.dto.BalanceRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FacadeMapperTest {

    private final UserDtoConverter userDtoConverter = new UserDtoConverter();

    @Test
    @DisplayName("controller 잔액 충전 DTO -> UserServiceResponse DTO로 전환: 해당 사항은 facade에서 변환")
    void testToServiceDto() {
        // given
        BalanceRequest request = new BalanceRequest(1L, 1000);

        // when
        UserServiceRequest serviceRequest = userDtoConverter.toServiceRequest(request);

        //then
        assertNotNull(serviceRequest);
        assertEquals(request.getUserId(), serviceRequest.getUserId());
        assertEquals(request.getAmount(), serviceRequest.getAmount());
    }
}
