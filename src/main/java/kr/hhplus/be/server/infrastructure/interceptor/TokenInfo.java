package kr.hhplus.be.server.infrastructure.interceptor;

public class TokenInfo {
    private final String status;
    private final int position;

    public TokenInfo(String status, int position) {
        this.status = status;
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

}
