package br.dev.ferreiras.calculatorWeb.config;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
Approach:

WebMvcConfigurator bean
 */
@Configuration
public class CorsSecurityConfiguration implements WebMvcConfigurer {

  @Bean
  public WebMvcConfigurer corsMessageConfiguration() {

    return new WebMvcConfigurer() {
      public void addCorsMapping(@NonNull CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**").allowedOrigins(
                "http://127.0.0.1:3000",
                "http://127.0.0.1:4200",
                "http://api.random.org:443"
        );
      };
    };
  }

}
