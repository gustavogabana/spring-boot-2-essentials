package academy.devdojo.springboot2.config;

import academy.devdojo.springboot2.domain.SystemUser;
import academy.devdojo.springboot2.service.SystemUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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

/**
 * Spring Filters:
 * BasicAuthenticationFilter: Verifies if the app has basic64 encode/decode
 * UsernamePasswordAuthentication: Checks if the app implements username and password authentication
 * DefaultLoginPageGeneratingFilter: Generates the basic login page
 * DefaultLogoutPageGeneratingFilter: Generates the basic logout page
 * FilterSecurityInterceptor: Check if the user is authorized
 * Authentication -> Authorization
 */
@Configuration // notes this class as a configuration and a bean
@Log4j2
@EnableMethodSecurity // enable the @PreAuthorize annotation used on controller, it's value is true by default
@RequiredArgsConstructor
public class SecurityConfig {
    /**
     * Setup that requires every http request needs to be authenticated with basic http authentication.
     * CSRF: Enable it to utilize the token on request that alter the status of the data in the database
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .authorizeHttpRequests((request) -> request
                        .anyRequest().authenticated()
                ).formLogin().and()
                .httpBasic(withDefaults());
        return http.build();
    }

    /**
     * Configures the user, the role of this user and the password of this user,
     * with cryptography provide with the password encoder object,
     * all in memory, that is, when the application is executing
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        log.info(encoder.encode("123"));
        UserDetails user = User.withUsername("gustavo")
                .password(encoder.encode("123"))
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

}
