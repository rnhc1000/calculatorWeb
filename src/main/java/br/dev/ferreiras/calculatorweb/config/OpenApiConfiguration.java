package br.dev.ferreiras.calculatorweb.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/** Support to OpenAPI 3.0
 * @author ricardo@ferreiras.dev.br
 * @version 2024.10.30.01
 * @since 1.0
 *
 */

@Configuration
public class OpenApiConfiguration {
  /**
   *
   * @return API UI
   */
  @Bean
  public OpenAPI defineOpenApi() {
    final Server server = new Server();
    server.setUrl("""
            http://192.168.15.11:8095/api/v1
            """);
    server.setDescription("Production");

    final Contact myContact = new Contact();
    myContact.setName(":Ricardo Ferreira");
    myContact.setEmail("ricardo@ferreiras.dev.br");

    final Info information = new Info()
            .title("Web Calculator")
            .version("2.1.1")
            .description("WebCalculatorAPI exposes endpoints to do maths at the backend and persists maths into a database")
            .contact(myContact);

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
