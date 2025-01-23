package kr.hhplus.be.server.domain.User.repository;

import kr.hhplus.be.server.domain.User.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long userId);

    Optional<User> save(User user);

    Optional<User> findByPessimisticLock(Long userId);
}
