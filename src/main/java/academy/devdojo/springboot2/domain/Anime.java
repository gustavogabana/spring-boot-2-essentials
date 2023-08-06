package academy.devdojo.springboot2.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data // lombok annotation that creates the getters, setters, equals & hashcode, to string
@AllArgsConstructor // creates constructor with all attributes
@NoArgsConstructor
@Entity // turns this class an entity for the database
@Builder // creates the builder for this class
public class Anime { // this class represents the objects
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // GENERATE THIS ID AS A PK
    private Long id;
    @NotEmpty(message = "The anime name cannot be empty")
    private String name;

    // the jackson serializer will attempt to generate the json file through the getters/setters methods
    // if they're not implemented, the no serializer found error will be thrown

}
