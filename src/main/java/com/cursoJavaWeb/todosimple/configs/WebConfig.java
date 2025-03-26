package com.cursoJavaWeb.todosimple.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    
    @Override  
    public void addCorsMappings(CorsRegistry registry) {  
        registry.addMapping("/**") // Permite todas as rotas  
                .allowedOrigins("http://127.0.0.1:5500") // Permite acessos apenas desta origem  
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos  
                .allowedHeaders("*") // Permite todos os cabeçalhos  
                .allowCredentials(true); // Permite envio de cookies  
    }  
}
