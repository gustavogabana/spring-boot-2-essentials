package academy.devdojo.springboot2.requests.userModelRequests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModelPostRequestBody {
    @NotEmpty(message = "The name cannot be empty")
    @NotNull(message = "The name cannot be null")
    private String name;

    @NotEmpty(message = "The username cannot be empty")
    @NotNull(message = "The username cannot be null")
    private String username;

    @NotEmpty(message = "The password cannot be empty")
    @NotNull(message = "The password cannot be null")
    private String password;

    @NotEmpty(message = "Authorities cannot be empty")
    @NotNull(message = "Authority cannot be null")
    private String authorities;

    @URL(message = "URL is not valid.")
    private String url;
}
