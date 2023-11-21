package relucky.code.technicaltask2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import relucky.code.technicaltask2.config.security.JwtFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static relucky.code.technicaltask2.common.enums.Role.ADMIN;
import static relucky.code.technicaltask2.common.enums.Role.USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;


    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( req -> req.requestMatchers(WHITE_LIST_URL).permitAll()
                        .requestMatchers("/api/v1/task/**").hasAnyRole(ADMIN.name(), USER.name())
                        .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
