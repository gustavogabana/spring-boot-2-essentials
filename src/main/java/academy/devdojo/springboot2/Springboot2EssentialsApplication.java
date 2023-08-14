package academy.devdojo.springboot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

/**
 * configures the beans needed for initiation
 * scan all the subpackages of the base package
 */
@SpringBootApplication
public class Springboot2EssentialsApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2EssentialsApplication.class, args);
        System.out.println(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("senha123"));
    }
    // needed to start the application
}
