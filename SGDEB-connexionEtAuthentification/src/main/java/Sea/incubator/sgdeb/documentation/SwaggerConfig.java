package Sea.incubator.sgdeb.documentation;



import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("Paiement API")
                .packagesToScan("Sea.incubator.sgdeb.controller")
                .build();
    }
}