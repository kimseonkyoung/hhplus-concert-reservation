package kr.hhplus.be.server.interfaces.api.dto;

import lombok.Data;

@Data
public class TokenResponse {
    private Long tokenUuid;
    private Long position;
    private String status;

    public TokenResponse(String uuid, Long wait, String i) {
    }
}
