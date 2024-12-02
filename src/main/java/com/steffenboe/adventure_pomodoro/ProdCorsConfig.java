package com.steffenboe.adventure_pomodoro;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@Profile("prod")
@EnableWebFlux
public class ProdCorsConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
          .allowedOrigins("https://adventure-pomodoro-j7inpp7h2q-ey.a.run.app/")
          .allowedMethods("*");
    }
}
