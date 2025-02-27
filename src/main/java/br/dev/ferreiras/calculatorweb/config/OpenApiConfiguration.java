package br.dev.ferreiras.calculatorweb.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Support to OpenAPI 3.0
 *
 * @author ricardo@ferreiras.dev.br
 * @version 2025.02.26.01
 * @since 1.0
 */

@Configuration
public class OpenApiConfiguration {
  /**
   * @return API UI
   */
  @Bean
  public OpenAPI defineOpenApi() {
    final Server server = new Server();
    server.setUrl("""
        http://192.168.0.12:8095
        """);
    server.setDescription("Production");

    final Contact myContact = new Contact();
    myContact.setName(":Ricardo Ferreira");
    myContact.setEmail("ricardo@ferreiras.dev.br");

    final Info information = new Info()
        .title("Web Calculator")
        .version("3.4.3.0")
        .description("WebCalculatorAPI exposes endpoints to do maths at the backend and persist " +
                     "results into a database")
        .contact(myContact)
        .license(new License()
            .name("Apache 2.0")
            .url("https://github.com/rnhc1000/calculatorWeb")
        );

    return new OpenAPI()
        .info(information)
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
        .components(
            new Components()
                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                )
        )
        .servers(List.of(server));
  }
}
