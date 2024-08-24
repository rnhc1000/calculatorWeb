package br.dev.ferreiras.calculatorWeb.config;

import jakarta.annotation.Nonnull;
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

      @Override
      public void addCorsMappings( @Nonnull CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**").allowedOrigins("http://192.168.15.11:7500",
                "http://127.0.0.1:80");
      }
    };
  }
}
