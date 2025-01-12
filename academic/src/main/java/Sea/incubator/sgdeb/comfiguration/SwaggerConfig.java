package Sea.incubator.sgdeb.comfiguration;


import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api") // Nom du groupe
                .packagesToScan("Sea.incubator.sgdeb.controller")
                .build();
    }

}
