package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
