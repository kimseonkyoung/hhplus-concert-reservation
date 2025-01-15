package kr.hhplus.be.server.domain.token;

import kr.hhplus.be.server.common.exception.ErrorCode;
import kr.hhplus.be.server.domain.mapper.service.TokenEntityConverter;
import kr.hhplus.be.server.domain.service.dto.TokenServiceResponse;
import kr.hhplus.be.server.domain.token.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;
    private final TokenGenerator tokenGenerator;

    public TokenService(TokenRepository tokenRepository, TokenGenerator tokenGenerator) {
        this.tokenRepository = tokenRepository;
        this.tokenGenerator = tokenGenerator;
    }

    //대량의 요청이 들어온다면 동시성 제어를 해야할 수도 있음.
    public TokenServiceResponse createToken(long userId) {
        // 0. DB에서 해당 userId로 Token 존재유무 확인
        Optional<Token> oldToken = tokenRepository.findById(userId);
        // 1. 기존 토큰 처리 (만료 상태로 변경)
        oldToken.ifPresent(token -> tokenRepository.updateTokenStatus(token.getTokenUuid(), TokenStatus.EXPIRED));
        // 3. 토큰 제조
        Token newToken = tokenGenerator.createToken();
        // 4. 토큰 저장
        tokenRepository.save(newToken);
        // 5. 토큰 대기열 반환
        int position = tokenRepository.selectTokenPosition(newToken.getTokenId());
        // 6. 반환할 토큰 responseDto로 셋팅
        TokenServiceResponse response = TokenEntityConverter.toServiceResponse(newToken, position);
        return response;
    }

    public TokenServiceResponse getTokenStatusAndPosition(String tokenUuid) {
        Token token = tokenRepository.getToken(tokenUuid)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.TOKEN_NOT_FOUND + tokenUuid));
        int position = tokenRepository.selectTokenPosition(token.getTokenId());
        TokenServiceResponse response = TokenEntityConverter.toServiceResponse(token, position);
        return response;
    }
}
