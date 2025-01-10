package kr.hhplus.be.server.interfaces.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceRequest {

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "충전금액", example = "10000")
    private Integer amount;


    public BalanceRequest(long userId, int amount) {
        this.userId = userId;
        this.amount = amount;
    }
}