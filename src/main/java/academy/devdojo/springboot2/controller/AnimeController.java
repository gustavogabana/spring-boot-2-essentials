package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.requests.animeRequests.AnimePostRequestBody;
import academy.devdojo.springboot2.requests.animeRequests.AnimePutRequestBody;
import academy.devdojo.springboot2.service.AnimeService;
import academy.devdojo.springboot2.util.DateUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("animes") // context for all the methods localhost:8080/anime
@Log4j2 // lombok annotation for logs
@AllArgsConstructor // creates a constructor with all attributes of the class
public class AnimeController { // controller represents all the endpoints of the app

    //@Autowired // annotation for dependency injection
    private final DateUtil dateUtil;
    private final AnimeService animeService;

    @GetMapping // context for this method only localhost:8080/anime/list
    @Operation(summary = "List all the animes paginated", description = "The default size is 20, use the parameter size to change the default value",
            tags = "{anime}")
    public ResponseEntity<Page<Anime>> list(@Parameter(hidden = true) Pageable page) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(animeService.listAll(page), HttpStatus.OK);
        // response entity object return the info(object) required and the corresponding http status
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Anime>> listAll() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(animeService.listAllNonPageable(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}") // path variable that will come along the url
    public ResponseEntity<Anime> findById(@PathVariable Long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
    }

    @GetMapping(path = "by-id/{id}") // path variable that will come along the url
    public ResponseEntity<Anime> findByIdAuthenticationPrincipal(@PathVariable Long id,
                                                                 @AuthenticationPrincipal UserDetails userDetails) {
        log.info(userDetails);
        return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<Anime>> findByName(@RequestParam(required = false) String name) {
        //@RequestParam annotation to map the value of the param for the url /find/name
        // it maps the name of the param of the method automatically
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.findByName(name));
    }

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')") // consider the role of the person that realizes the request
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody anime) {// jackson serializer will map the object sent through http
        // @valid checks if the object matches the attributes requisites
        // @requestbody checks if the json body matches the endpoint fields and structure
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(animeService.save(anime), HttpStatus.CREATED);
    }

    // delete method based on rfc7231 idempotent methods
    @DeleteMapping(path = "/admin/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "Doesn't Exist in The Database")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // also idempotent method
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody anime) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        animeService.replace(anime);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // return no content because we already have the data at the requisition
    }

}
