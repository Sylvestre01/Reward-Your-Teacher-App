package com.decagon.rewardyourteacherapi11bjavapodf2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

@Configuration
@EnableSwagger2

public class SwaggerConfig implements WebMvcConfigurer{
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private ApiInfo apiInfo() {
        return new ApiInfo("REWARD YOUR TEACHER",
                "An app that help students reward their teacher",
                "1.0",
                "Terms of service",
                new Contact("Decagon SQ11B POD F", "https://rewardyourteacher.com/about", "chukwuma.egwuonwu@decagon.dev"),
                "API License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                Collections.emptyList());
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(asList(securityContext()))
                .securitySchemes(asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.decagon.rewardyourteacherapi11bjavapodf2.controllers"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT",AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global","accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return asList(new SecurityReference("JWT", authorizationScopes));
    }




}
