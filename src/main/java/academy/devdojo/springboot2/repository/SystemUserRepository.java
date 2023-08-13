package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {
    SystemUser findByUsername(String username);
}
