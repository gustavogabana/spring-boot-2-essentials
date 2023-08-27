package academy.devdojo.springboot2.requests.animeRequests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimePutRequestBody {
    // a dto look alike class that serves as an intermediate between the entity and the crud methods
    // contains the id, so it can execute the update when send through the jpa save method in the update java endpoint
    @NotNull(message = "ID cannot be null.")
    private Long id;
    private String name;
}