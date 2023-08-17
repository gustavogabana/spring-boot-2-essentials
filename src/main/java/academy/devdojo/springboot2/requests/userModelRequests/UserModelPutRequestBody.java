package academy.devdojo.springboot2.requests.userModelRequests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModelPutRequestBody {
    @NotNull(message = "ID cannot be null")
    private Long id;

    private String name;

    private String username;

    private String password;

    private String authorities;
}
