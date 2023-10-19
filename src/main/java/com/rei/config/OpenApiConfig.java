package com.rei.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI CustomOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Restful API with Java and Spring Boot")
                        .version("v1")
                        .description("Lorem Ipsum Dolor Amet")
                        .termsOfService("https://longliverei.github.io/")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")
                        )
                );
    }
}
