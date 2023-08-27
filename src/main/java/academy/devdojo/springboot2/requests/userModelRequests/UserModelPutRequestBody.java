package academy.devdojo.springboot2.requests.userModelRequests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty
    @Schema(description = "This is the UserModel name")
    private String name;
    @NotEmpty
    @Schema(description = "This is the UserModel username")
    private String username;
    @NotEmpty
    @Schema(description = "This is the UserModel password")
    private String password;
    @NotEmpty
    @Schema(description = "This is the UserModel authorities")
    private String authorities;
}
