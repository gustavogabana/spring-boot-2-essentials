package academy.devdojo.springboot2.integration;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.domain.UserModel;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.repository.UserModelRepository;
import academy.devdojo.springboot2.requests.animeRequests.AnimePostRequestBody;
import academy.devdojo.springboot2.util.anime.AnimeCreator;
import academy.devdojo.springboot2.util.anime.AnimePostRequestBodyCreator;
import academy.devdojo.springboot2.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// run the application for the integration test
@AutoConfigureTestDatabase // self-explanatory database for tests configuration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
// spring will consider the context dirty and will drop the database before each test
public class AnimeControllerIT {
    @Autowired
    @Qualifier(value = "testRestTemplateUser") // tells to spring which dependency inject
    private TestRestTemplate testRestTemplateUser;
    @Autowired
    @Qualifier(value = "testRestTemplateAdmin") // tells to spring which dependency inject
    private TestRestTemplate testRestTemplateAdmin;
    @Autowired
    private AnimeRepository animeRepository;
    @Autowired
    private UserModelRepository userModelRepository;
    private static final UserModel ADMIN = UserModel.builder()
            .username("gustavo")
            .password("{bcrypt}$2a$10$cKO0WbdeynWpOoYaqsIqYeELGbNAH36H7xBJ9kN2yByKCN1fgAkQy")
            .name("Gustavo")
            .authorities("ROLE_USER,ROLE_ADMIN")
            .build();
    private static final UserModel USER = UserModel.builder()
            .username("teste")
            .password("{bcrypt}$2a$10$cKO0WbdeynWpOoYaqsIqYeELGbNAH36H7xBJ9kN2yByKCN1fgAkQy")
            .name("TESTE")
            .authorities("ROLE_USER")
            .build();

    @TestConfiguration
    static class Config {
        @Bean(name = "testRestTemplateUser") //testRestTemplateUserAdmin
        @Lazy
        public TestRestTemplate testRestTemplateUserCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication(USER.getUsername(), "senha123");
            return new TestRestTemplate(restTemplateBuilder);
        }

        @Bean(name = "testRestTemplateAdmin")
        @Lazy
        public TestRestTemplate testRestTemplateAdminCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication(ADMIN.getUsername(), "senha123");
            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @Test
    @DisplayName("List returns list of anime inside page object when successful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
        Anime validAnime = AnimeCreator.createValidAnime();
        Anime saved = animeRepository.save(validAnime);
        UserModel savedUserModel = userModelRepository.save(USER);
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(savedUserModel.getUsername(), "senha123");
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        PageableResponse<Anime> anime = testRestTemplateUser.exchange("/animes", HttpMethod.GET, requestEntity,
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
        UserModel savedUserModel = userModelRepository.save(USER);
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(savedUserModel.getUsername(), "senha123");
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        List<Anime> animes = testRestTemplateUser.exchange("/animes/all", HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(saved.getName());
    }

    @Test
    @DisplayName("Find By Id returns anime when successful")
    void findById_ReturnsAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createValidAnime());
        userModelRepository.save(USER);
        Long expectedId = savedAnime.getId();
        Anime anime = testRestTemplateUser.getForObject("/animes/{id}", Anime.class, expectedId);

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Find By Name returns list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createValidAnime());
        UserModel savedUserModel = userModelRepository.save(USER);
        String expectedName = savedAnime.getName();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(savedUserModel.getUsername(), "senha123");
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        String url = String.format("/animes/find?name=%s", expectedName);
        List<Anime> animes = testRestTemplateUser.exchange(url, HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isNotNull().isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Find By Name returns an empty list when anime is not found")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound() {
        String expectedName = "Berserk";
        UserModel savedUserModel = userModelRepository.save(USER);
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(savedUserModel.getUsername(), "senha123");
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        String url = String.format("/animes/find?name=%s", expectedName);
        List<Anime> animes = testRestTemplateUser.exchange(url, HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("Save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {
        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();
        userModelRepository.save(USER);
        ResponseEntity<Anime> entity = testRestTemplateUser.postForEntity("/animes", animePostRequestBody, Anime.class);

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(entity.getBody()).isNotNull();
        Assertions.assertThat(entity.getBody().getId()).isNotNull().isEqualTo(1);
    }

    @Test
    @DisplayName("Replace updates anime when successful")
    void replace_UpdatesAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createValidAnime());
        userModelRepository.save(USER);
        savedAnime.setName("Castlevania");
        ResponseEntity<Void> entity = testRestTemplateUser.exchange("/animes", HttpMethod.PUT, new HttpEntity<>(savedAnime), Void.class);

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createValidAnime());
        UserModel savedUserModel = userModelRepository.save(ADMIN);
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(savedUserModel.getUsername(), "senha123");
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Void> entity = testRestTemplateAdmin.exchange("/animes/admin/".concat(savedAnime.getId().toString()), HttpMethod.DELETE,
                requestEntity, Void.class);

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Delete return 403 when user is not admin")
    void delete_Returns403_WhenUserIsNotAdmin() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createValidAnime());
        UserModel savedUserModel = userModelRepository.save(USER);
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(savedUserModel.getUsername(), "senha123");
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Void> entity = testRestTemplateUser.exchange("/animes/admin/{id}", HttpMethod.DELETE,
                requestEntity, Void.class, savedAnime.getId());

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
