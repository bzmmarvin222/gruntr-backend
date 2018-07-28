package de.brockhausag.gruntr.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(name="swagger.enabled", havingValue="true")
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .securitySchemes(Arrays.asList(new BasicAuth("basicAuth")))
//                .ignoredParameterTypes(AppUser.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("de.brockhausag"))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Gruntr Rest API",
                "Documentation of basic REST features of Gruntr",
                "v.0.0.1",
                "None",
                new Contact("Marvin Fittinghoff", "www.brockhaus-ag.de","mfittinghoff@brockhaus-ag.de"),
                "MIT",
                "https://github.com/bzmmarvin222/gruntr-backend/blob/master/LICENSE",
                Collections.emptyList());
    }
}
