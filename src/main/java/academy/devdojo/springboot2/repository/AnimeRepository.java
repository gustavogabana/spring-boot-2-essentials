package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // notes this class as a repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {
    // reposirtoy represents the connection with the database

    List<Anime> findByName(String name);
}
