package kr.hhplus.be.server.infrastructure.orm.User;


import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;


public interface JpaUserRepository extends JpaRepository<User, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM User u where u.userId = :userId")
    User findByPessimisticLock(Long userId);
}
