package com.crm.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// Get the documentation at this url http://localhost:8088/swagger-ui.html#/
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.crm.app"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "Spring Boot Swagger2 Example API",
                null,
                "1.0",
                "Terms of Service",
                new Contact("Maroussia Arnault", "https://www.maroussiaarnault.com",
                        "contact@maroussiaarnault.com").toString(),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/"
        );

        return apiInfo;
    }

    private class Contact {
        public Contact(String name, String email, String website) {
        }
    }
}
