package rw.mugaboandre.rentalhub.config;

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

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Create security requirement
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("bearerAuth");

        return new OpenAPI()
                // Add global security requirement
                .addSecurityItem(securityRequirement)

                // Define security scheme
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        ))
                // API info
                .info(new Info()
                        .title("RentalHub API")
                        .version("1.0.0")
                        .description("Comprehensive API documentation for the RentalHub backend, supporting management of users (Admin, Client, Owner), properties, lease contracts, payments, and notifications")
                        .contact(new Contact()
                                .name("Andre Mugabo")
                                .email("andremugabo@yahoo.fr")
                                .url("https://andremugabo.rw"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                // Server information
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Development Server"),
                        new Server()
                                .url("https://your-production-url.com")
                                .description("Production Server")
                ));
    }
}