package academy.devdojo.springboot2.integration;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import academy.devdojo.springboot2.util.AnimeCreator;
import academy.devdojo.springboot2.util.AnimePostRequestBodyCreator;
import academy.devdojo.springboot2.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// run the application for the integration test
@AutoConfigureTestDatabase // self-explanatory database for tests configuration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // spring will consider the context dirty and will drop the database before each test
public class AnimeControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private AnimeRepository animeRepository;
    @LocalServerPort // gets the port which the test is running
    private int port;

    @Test
    @DisplayName("List returns list of anime inside page object when successful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
        Anime validAnime = AnimeCreator.createValidAnime();
        Anime saved = animeRepository.save(validAnime);

        PageableResponse<Anime> anime = testRestTemplate.exchange("/animes", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Anime>>() {
                }).getBody();

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(anime.toList().get(0).getName()).isEqualTo(saved.getName());
    }

    @Test
    @DisplayName("List All returns list of animes when successful")
    void listAll_ReturnsListOfAnimes_WhenSuccessful() {
        Anime validAnime = AnimeCreator.createValidAnime();
        Anime saved = animeRepository.save(validAnime);

        List<Anime> animes = testRestTemplate.exchange("/animes/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(saved.getName());
    }

    @Test
    @DisplayName("Find By Id returns anime when successful")
    void findById_ReturnsAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createValidAnime());
        Long expectedId = savedAnime.getId();
        Anime anime = testRestTemplate.getForObject("/animes/{id}", Anime.class, expectedId);

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Find By Name returns list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createValidAnime());
        String expectedName = savedAnime.getName();
        String url = String.format("/animes/find?name=%s", expectedName);
        List<Anime> animes = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isNotNull().isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Find By Name returns an empty list when anime is not found")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound() {
        String expectedName = "Berserk";
        String url = String.format("/animes/find?name=%s", expectedName);
        List<Anime> animes = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("Save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {
        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();
        ResponseEntity<Anime> entity = testRestTemplate.postForEntity("/animes", animePostRequestBody, Anime.class);

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(entity.getBody()).isNotNull();
        Assertions.assertThat(entity.getBody().getId()).isNotNull().isEqualTo(1);
    }

    @Test
    @DisplayName("Replace updates anime when successful")
    void replace_UpdatesAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createValidAnime());
        savedAnime.setName("Castlevania");

        ResponseEntity<Void> entity = testRestTemplate.exchange("/animes", HttpMethod.PUT, new HttpEntity<>(savedAnime), Void.class);

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createValidAnime());

        ResponseEntity<Void> entity = testRestTemplate.exchange("/animes/{id}", HttpMethod.DELETE,
                null, Void.class, savedAnime.getId());

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
