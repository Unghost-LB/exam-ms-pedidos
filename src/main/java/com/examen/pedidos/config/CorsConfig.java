package com.examen.pedidos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Aplica a todos los controladores (pedidos, productos, etc)
                        .allowedOrigins("*") // Permite peticiones de tu localhost de React
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") // ¡CLAVE! Habilita PUT y OPTIONS
                        .allowedHeaders("*");
            }
        };
    }
}