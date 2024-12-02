package com.steffenboe.adventure_pomodoro;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@Profile("!prod")
@EnableWebFluxSecurity
public class UnsecuredSecurityConfig {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/market").permitAll()
                        .pathMatchers("/").permitAll()
                        .pathMatchers("/static/**").permitAll()
                        .pathMatchers("/favicon.ico").permitAll()
                        .pathMatchers("/manifest.json").permitAll()
                        .pathMatchers("/tasks").permitAll()
                        .pathMatchers("/rewards").permitAll()
                        .pathMatchers("/player").permitAll()
                        .pathMatchers("/logo192.png").permitAll()
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyExchange().authenticated())
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(withDefaults()));
        return http.build();
    }

}
