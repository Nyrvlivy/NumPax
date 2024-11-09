package br.com.numpax.infrastructure.config;

import br.com.numpax.application.services.UserService;
import br.com.numpax.infrastructure.config.auth.JwtAuthenticationFilter;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;

public class WebConfig {
    public static void configureFilters(ServletContext servletContext, UserService userService) {
        FilterRegistration.Dynamic jwtFilter = servletContext.addFilter(
            "jwtAuthenticationFilter", 
            new JwtAuthenticationFilter(userService)
        );
        jwtFilter.addMappingForUrlPatterns(null, false, "/api/v1/*");
    }
} 