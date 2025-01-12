package Sea.incubator.sgdeb.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggeurConfig {

    @Bean
    public GroupedOpenApi publicApis() {
        return GroupedOpenApi.builder()
                .group("public-api") // Nom du groupe
                .packagesToScan("Sea.incubator.sgdeb.controller")
                .build();
    }

}
