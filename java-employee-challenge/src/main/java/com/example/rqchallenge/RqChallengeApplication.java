package com.example.rqchallenge;

import com.example.rqchallenge.employees.EmployeeService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot REST API Documentation",
                description = "Spring Boot REST API Documentation",
                version = "v1.0",
                contact = @Contact(
                        name = "Dimpal",
                        email = "dimpal.rojmala@gmail.com"
                )
        ))
@SpringBootApplication
public class RqChallengeApplication {


    private static final Logger LOGGER = LoggerFactory.getLogger(RqChallengeApplication.class);

    public static void main(String[] args) {
        LOGGER.info("Starting Application ");
        SpringApplication.run(RqChallengeApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

}
