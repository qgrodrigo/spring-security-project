package com.autonoma.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de control de movimiento de productos")
                        .version("1.0")
                        .description("API para gestión de usuarios, roles y personal en el sistema Security_DB")
                        );
    }
}
