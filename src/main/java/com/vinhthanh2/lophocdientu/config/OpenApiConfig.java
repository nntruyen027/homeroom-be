package com.vinhthanh2.lophocdientu.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    // ⭐ Chỉ quét controller trong package này
    @Bean
    public GroupedOpenApi apiControllers() {
        return GroupedOpenApi.builder()
                .group("LopHocDienTu-API")
                .packagesToScan("com.vinhthanh2.lophocdientu.controller")
                .build();
    }

    // ⭐ Cấu hình OpenAPI + JWT
    @Bean
    public OpenAPI api() {

        return new OpenAPI()

                .info(new Info()
                        .title("Sổ chủ nhiệm điện tử API")
                        .version("1.0.0")
                        .description("Tài liệu API cho hệ thống Sổ chủ nhiệm điện tử")
                        .contact(new Contact()
                                .name("Nguyễn Ngọc Truyện")
                                .email("nntruyen027@gmail.com"))
                        .license(new License().name("Apache 2.0"))
                )

                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))

                .components(new Components().addSecuritySchemes(
                        "BearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .name("Authorization")
                                .in(SecurityScheme.In.HEADER)
                ));
    }
}
