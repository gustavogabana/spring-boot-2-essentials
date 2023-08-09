package academy.devdojo.springboot2.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * For more information about this bean, search for Spring Security Filter Chain.
 */
@Configuration // notes this class as a configuration and a bean
@Log4j2
public class SecurityConfig {
    /**
     * Setup that requires every http request needs to be authenticated with basic http authentication
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> auth
                .anyRequest().authenticated()
        ).httpBasic(withDefaults());
        return http.build();
    }

    /**
     * Configures the user, the role of this user and the password of this user,
     * with cryptography provide with the password encoder object,
     * all in memory, that is, when the application is executing
     *
     * @return
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        log.info("Password encoded '{}'", encoder.encode("test"));
        UserDetails user = User.withUsername("gustavo")
                .password(encoder.encode("123"))
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

}
