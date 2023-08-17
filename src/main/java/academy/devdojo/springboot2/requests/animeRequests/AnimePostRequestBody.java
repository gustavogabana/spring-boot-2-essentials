package academy.devdojo.springboot2.requests.animeRequests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimePostRequestBody {
    // a dto look alike class that serves as an intermediate between the entity and the crud methods
    // does not contain the id attribute
    @NotEmpty(message = "The anime name cannot be empty")
    @NotNull(message = "The anime name cannot be null")
    private String name;
    @URL(message = "The URL is not valid") // return this message
    private String url;
}
