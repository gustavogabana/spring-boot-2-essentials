package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserModelRepository extends JpaRepository<UserModel, Long> {

    UserModel findByUsername(String username);

    List<UserModel> findByName(String name);

}
