package academy.devdojo.springboot2.requests;

import lombok.Data;

@Data
public class AnimePutRequestBody {
    // A DTO LOOK ALIKE CLASS THAT SERVES AS A INTERMEDIATE BETWEEN THE ENTITY AND THE CRUD METHODS
    // CONTAINS THE ID SO IT CAN EXECUTE THE UPDATE WHEN SEND THROUGH THE JPA SAVE METHOD IN THE UPDATE JAVA ENDPOINT
    private Long id;
    private String name;
}
