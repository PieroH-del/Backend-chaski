package com.chaski.Backend_chaski.config;

import com.chaski.Backend_chaski.security.FirebaseAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private FirebaseAuthInterceptor firebaseAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(firebaseAuthInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                    "/api/usuarios/registro",
                    "/api/usuarios/login",
                    "/api/usuarios/login/**"
                );
    }
}

