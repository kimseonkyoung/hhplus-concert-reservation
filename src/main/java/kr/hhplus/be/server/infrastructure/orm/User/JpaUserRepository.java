package kr.hhplus.be.server.infrastructure.orm.User;


import kr.hhplus.be.server.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JpaUserRepository extends JpaRepository<User, Long> {

}
