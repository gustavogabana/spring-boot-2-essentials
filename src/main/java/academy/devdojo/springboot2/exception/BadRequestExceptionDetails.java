package academy.devdojo.springboot2.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder // To use to builder from the extendes class
public class BadRequestExceptionDetails extends ExceptionDetails {

}
