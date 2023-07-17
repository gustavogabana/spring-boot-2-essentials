package academy.devdojo.springboot2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // the status that this exception will always return
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
