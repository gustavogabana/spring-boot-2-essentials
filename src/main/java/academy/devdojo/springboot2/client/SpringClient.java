package academy.devdojo.springboot2.client;

import academy.devdojo.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        //Rest Template: synchronous client used to perform HTTP requests.

        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 2);
        log.info(entity);

        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 3);
        log.info(object);

        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info(Arrays.toString(animes));

        ResponseEntity<List<Anime>> animesList = new RestTemplate().exchange("http://localhost:8080/animes/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        log.info(animesList.getBody());

        Anime anime = new Anime(null, "Sword Art Online");
        Anime posted = new RestTemplate().postForObject("http://localhost:8080/animes", anime, Anime.class);
        log.info("Saved anime: {}", posted);

        Anime savedAnime = new Anime(null, "CDZ");
        ResponseEntity<Anime> animeSaved = new RestTemplate().exchange("http://localhost:8080/animes",
                HttpMethod.POST,
                new HttpEntity<>(savedAnime, createdJsonHeader()),
                Anime.class);
        log.info("Saved anime: {}", animeSaved);

        Anime body = animeSaved.getBody();
        body.setName("CDZ Saint Seiya");

        ResponseEntity<Void> animeUpdated = new RestTemplate().exchange("http://localhost:8080/animes",
                HttpMethod.PUT,
                new HttpEntity<>(body, createdJsonHeader()),
                Void.class);
        log.info("Updated anime: {}", animeUpdated);

        ResponseEntity<Void> animeDeleted = new RestTemplate().exchange("http://localhost:8080/animes/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                7);
        log.info("Deleted anime: {}", animeDeleted);

    }

    private static HttpHeaders createdJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
