package br.dev.ferreiras.calculatorWeb.config;

import jakarta.annotation.Nonnull;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
Approach:
WebMvcConfigurator bean
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class CorsSecurityConfiguration implements WebMvcConfigurer {

  @Bean
  public WebMvcConfigurer corsMessageConfiguration() {
    return new WebMvcConfigurer() {

      @Override
      public void addCorsMappings(@Nonnull CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                    .allowedOrigins(
                            "https://3.19.135.188:7500",
                            "https://calculatorweb.ferreiras.dev.br:443",
                            "http://192.168.15.11:7500",
                            "http://127.0.0.1:7500",
                            "http://localhost:7500",
                            "http://localhost"
                    )
                    .allowedMethods("GET", "POST", "PATCH", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT")
                    .allowedHeaders("*")
                    .allowCredentials(true);
      }
    };
  }
}
