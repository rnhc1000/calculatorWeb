package br.dev.ferreiras.calculatorWeb.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {
  @Bean
  public OpenAPI defineOpenApi() {
    Server server = new Server();
    server.setUrl("http://127.0.0.1:8095/");
    server.setDescription("Development");

    Contact myContact = new Contact();
    myContact.setName("Ricardo Ferreira");
    myContact.setEmail("ricardo@ferreiras.dev.br");

    Info information = new Info()
            .title("Web Calculator")
            .version("1.0")
            .description("API Calculator!")
            .contact(myContact);

    return new OpenAPI().info(information).servers(List.of(server));
  }
}
