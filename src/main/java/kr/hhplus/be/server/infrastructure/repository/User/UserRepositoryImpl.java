package kr.hhplus.be.server.infrastructure.repository.User;

import kr.hhplus.be.server.common.log.AllRequiredLogger;
import kr.hhplus.be.server.domain.User.User;
import kr.hhplus.be.server.domain.User.repository.UserRepository;
import kr.hhplus.be.server.infrastructure.orm.User.JpaUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllRequiredLogger
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Optional<User> findById(Long userId) {
        return jpaUserRepository.findById(userId);
    }

    @Override
    public Optional<User> save(User user) {
        return Optional.of(jpaUserRepository.save(user));
    }
}
