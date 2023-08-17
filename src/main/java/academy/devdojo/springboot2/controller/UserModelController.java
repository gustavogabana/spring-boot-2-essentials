package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.UserModel;
import academy.devdojo.springboot2.requests.userModelRequests.UserModelPostRequestBody;
import academy.devdojo.springboot2.requests.userModelRequests.UserModelPutRequestBody;
import academy.devdojo.springboot2.service.UserModelService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
@Log4j2
@AllArgsConstructor
public class UserModelController {

    private final UserModelService userModelService;

    @GetMapping
    public ResponseEntity<Page<UserModel>> list(Pageable page) {
        return new ResponseEntity<>(userModelService.listAllPageable(page), HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<UserModel>> listAll() {
        return new ResponseEntity<>(userModelService.listAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserModel> findById(@PathVariable Long id) {
        Optional<UserModel> optional = userModelService.findById(id);
        return optional.map(userModel -> new ResponseEntity<>(userModel, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/findByName")
    public ResponseEntity<List<UserModel>> findByName(@RequestParam String name) {
        if (name.isEmpty() || name.isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (userModelService.findByName(name).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(userModelService.findByName(name));
        }
    }

    @PostMapping
    public ResponseEntity<UserModel> save(@RequestBody @Valid UserModelPostRequestBody userModelPostRequestBody) {
        String authorities = userModelPostRequestBody.getAuthorities();
        if (!validateAuthority(authorities)) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        if (checkIfAlreadyExists(userModelPostRequestBody)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        userModelPostRequestBody.setPassword(encodePassword(userModelPostRequestBody.getPassword()));
        return new ResponseEntity<>(userModelService.save(userModelPostRequestBody), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody @Valid UserModelPutRequestBody userModelPutRequestBody) {
        userModelPutRequestBody.setPassword(encodePassword(userModelPutRequestBody.getPassword()));
        userModelService.replace(userModelPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userModelService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private String encodePassword(String password) {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password);
    }

    private boolean validateAuthority(String authority) {
        if (!authority.contains("_") || !authority.contains("USER") ||
                !authority.contains("ADMIN") || !authority.contains("ROLE") || !authority.startsWith("ROLE")) {
            if (authority.contains(",")) {
                int commaIndex = authority.indexOf(",");
                String first = authority.substring(0, commaIndex);
                String second = authority.substring(commaIndex);
                return first.equalsIgnoreCase("ROLE_USER") || first.equalsIgnoreCase("ROLE_ADMIN") ||
                        second.equalsIgnoreCase("ROLE_USER") || second.equalsIgnoreCase("ROLE_ADMIN");
            } else {
                return authority.equalsIgnoreCase("ROLE_USER") || authority.equalsIgnoreCase("ROLE_ADMIN");
            }
        } else {
            return false;
        }
    }

    private boolean checkIfAlreadyExists(UserModelPostRequestBody userModelPostRequestBody) {
        List<UserModel> userModels = userModelService.listAll();
        for (UserModel userModel : userModels) {
            if (userModel.getName().equals(userModelPostRequestBody.getName()) &&
                    userModel.getUsername().equals(userModelPostRequestBody.getUsername())) {
                return true;
            }
        }
        return false;
    }
}
