package academy.devdojo.springboot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// CONFIGURES THE BEANS NEEDED FOR INITIATION
// SCAN ALL THE SUBPACKAGES OF THE BASE PACKAGE
@SpringBootApplication
public class Springboot2EssentialsApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot2EssentialsApplication.class, args);
	}
	// needed to start the application
}
