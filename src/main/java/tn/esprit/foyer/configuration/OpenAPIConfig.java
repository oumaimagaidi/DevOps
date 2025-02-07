package tn.esprit.foyer.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.apache.catalina.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI openApiInformation() {
        Contact contact = new Contact().email("springdoc@gmail.com").name("ghassen hammouda");
        Info info = new Info().contact(contact).description("Spring Boot Foyer Project")
                .summary("Manage Application with web services for ASI II Course")
                .title("Study Case Foyer").version("1.0.0")
                .license(new License().name("Apache 2.0").url("http://springdoc.org"));
        return new OpenAPI().info(info);
    }
}

