package kr.hhplus.be.server.domain.Token;

import kr.hhplus.be.server.domain.token.Token;
import kr.hhplus.be.server.domain.token.TokenGenerator;
import kr.hhplus.be.server.domain.token.TokenStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TokenGeneratorTest {

    private TokenGenerator tokenGenerator;

    @BeforeEach
    void setUp() {
        tokenGenerator = new TokenGenerator(); // 실제 구현체
    }

    @Test
    void shouldGenerateValidToken() {
        // When
        Token generatedToken = tokenGenerator.createToken(1);

        // Then
        assertNotNull(generatedToken);
        assertNotNull(generatedToken.getTokenUuid());
        assertEquals(TokenStatus.ACTIVE, generatedToken.getStatus());
        assertTrue(generatedToken.getTokenUuid().length() > 0); // UUID 길이 확인
        System.out.println("TokenUuid = " + generatedToken.getTokenUuid());
        System.out.println("Status = " + generatedToken.getStatus());
        System.out.println("CreatedAt = " + generatedToken.getCreatedAt());
        System.out.println("ExpiredAt = " + generatedToken.getExpiredAt());
    }

    @Test
    void shouldGenerateUniqueTokens() {
        // When
        Token token1 = tokenGenerator.createToken(1);
        Token token2 = tokenGenerator.createToken(1);

        // Then
        assertNotEquals(token1.getTokenUuid(), token2.getTokenUuid());
        System.out.println("TokenUuid = " + token1.getTokenUuid());
        System.out.println("TokenUuid = " + token2.getTokenUuid());
    }
}

