package com.woonit.wonnit.global.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Configuration
    class OpenApiConfig {

        @Bean
        fun customOpenAPI(): OpenAPI =
            OpenAPI().info(
                Info()
                    .title("WONNIT API")
                    .version("v1")
                    .description("API Docs (Swagger UI: /swagger-ui/index.html)")
            )
    }
}