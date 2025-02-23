package kr.hhplus.be.server.infrastructure.repository.Token;

import kr.hhplus.be.server.domain.token.Token;
import kr.hhplus.be.server.domain.token.TokenStatus;
import kr.hhplus.be.server.domain.token.repository.TokenRepository;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.redisson.api.RMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Repository
public class TokenRepositoryImpl implements TokenRepository {

    private final RedissonClient redissonClient;
    private static final String TOKEN_PREFIX = "token:";
    private static final int TTL_SECONDS = 300;
    private static final String WAITING_QUEUE_KEY = "token-waiting-queue";
    private static final String ACTIVE_QUEUE_KEY = "token-active-queue";

    public TokenRepositoryImpl(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void save(Token token) {
        RMap<String, String> tokenMap = redissonClient.getMap(TOKEN_PREFIX + token.getTokenUuid());
        tokenMap.put("uuid", token.getTokenUuid());
        tokenMap.put("status", token.getStatus().name());
        tokenMap.put("createdAt", token.getCreatedAt().toString());

        if (token.getStatus() == TokenStatus.ACTIVE) {
            tokenMap.expire(TTL_SECONDS, TimeUnit.SECONDS);
        }
    }

    @Override
    public Optional<Token> findById(String tokenUuid) {
        RMap<String, String> tokenMap = redissonClient.getMap(TOKEN_PREFIX + tokenUuid);
        if (tokenMap.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new Token(
                tokenMap.get("uuid"),
                TokenStatus.valueOf(tokenMap.get("status")),
                LocalDateTime.parse(tokenMap.get("createdAt"))
        ));
    }

    @Override
    public void updateTokenStatus(String tokenUuid, TokenStatus status) {
        RMap<String, String> tokenMap = redissonClient.getMap(TOKEN_PREFIX + tokenUuid);
        if (!tokenMap.isEmpty()) {
            tokenMap.put("status", status.name());
        }
    }

    @Override
    public void deleteByUserId(long userId, TokenStatus status) {
        String tokenKey = TOKEN_PREFIX + userId;
        RMap<String, String> tokenMap = redissonClient.getMap(tokenKey);

        // 해당 userId의 ACTIVE 토큰이 있는 경우 삭제
        if (!tokenMap.isEmpty() && tokenMap.containsKey("status") && tokenMap.get("status").equals(status.name())) {
            String tokenUuid = tokenMap.get("uuid");
            tokenMap.delete();

            // 대기열에서도 제거
            redissonClient.getScoredSortedSet(ACTIVE_QUEUE_KEY).remove(tokenUuid);
            redissonClient.getScoredSortedSet(WAITING_QUEUE_KEY).remove(tokenUuid);
        }
    }

    @Override
    public List<String> findWaitingTokens(int count) {
        RScoredSortedSet<String> waitingQueue = redissonClient.getScoredSortedSet(WAITING_QUEUE_KEY);
        return waitingQueue.stream().limit(count).collect(Collectors.toList());
    }

    @Override
    public void moveTokenToActiveQueue(String tokenUuid) {
        RScoredSortedSet<String> waitingQueue = redissonClient.getScoredSortedSet(WAITING_QUEUE_KEY);
        RScoredSortedSet<String> activeQueue = redissonClient.getScoredSortedSet(ACTIVE_QUEUE_KEY);

        // WAITING 대기열에서 제거
        waitingQueue.remove(tokenUuid);
        // ACTIVE 대기열에 추가
        activeQueue.add(System.currentTimeMillis(), tokenUuid);
    }

    @Override
    public int countActiveTokens() {
        RScoredSortedSet<String> activeQueue = redissonClient.getScoredSortedSet(ACTIVE_QUEUE_KEY);
        return activeQueue.size();
    }
}
