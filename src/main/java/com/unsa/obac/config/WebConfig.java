package com.unsa.obac.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.unsa.obac.middleware.IPInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private IPInterceptor ipInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipInterceptor)
                .addPathPatterns("/**") // Aplica el interceptor a todas las rutas
                .excludePathPatterns("/auth/login", "/auth/register"); // Excluye login y registro
    }
}
