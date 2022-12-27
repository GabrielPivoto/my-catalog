package br.com.inatel.MyCatalog.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class configures the Swagger.
 *
 * @author Gabriel Pivoto
 * @version JDK 1.7
 * @since 1.0
 */
@Configuration
@ConditionalOnProperty(name = "springdoc.swagger-ui.enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerConfiguration {

    @Bean
    public OpenAPI api() {
        return new OpenAPI().info(info());
    }

    private Info info() {
        String title = "MyCatalog API";
        String description = "App to register movies and series";
        String version = "1.0";
        String contactName = "Gabriel Pivoto";
        String contactEmail = "gabriel.pivoto@inatel.br";
        String contactUrl = "https://gabrielpivoto.github.io/";
        return new Info()
                .title(title)
                .description(description)
                .version(version)
                .contact(new Contact()
                        .name(contactName)
                        .email(contactEmail)
                        .url(contactUrl));
    }

}
