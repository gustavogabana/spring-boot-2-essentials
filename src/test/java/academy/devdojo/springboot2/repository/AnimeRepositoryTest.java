package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest // notes this class as jpa test class so transactions rollback at the end of each test
@DisplayName("Tests for Anime Repository")
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save persists anime when successful")
    void save_PersistAnime_WhenSuccessful() {
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        Assertions.assertThat(animeSaved).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates anime when successful")
    void save_UpdateAnime_WhenSuccessful() {
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        animeSaved.setName("Castlevania");
        Anime animeUpdated = this.animeRepository.save(animeSaved);
        List<Anime> animeList = this.animeRepository.findByName(animeUpdated.getName());

        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());
        Assertions.assertThat(animeList.size()).isGreaterThanOrEqualTo(1);
        Assertions.assertThat(animeList.get(0).getId()).isEqualTo(animeUpdated.getId());
        Assertions.assertThat(animeList.get(0).getName()).isEqualTo(animeUpdated.getName());
    }

    private Anime createAnime() {
        return new Anime(null, "Berserk");
    }
}