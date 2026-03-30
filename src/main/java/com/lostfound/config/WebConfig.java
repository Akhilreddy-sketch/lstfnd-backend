package com.lostfound.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Enable Credentials
        config.setAllowCredentials(true);

        // Allowed Origins
        config.setAllowedOriginPatterns(Arrays.asList(
                "https://lstfnd-frontend-g31tz66r2-akhilreddy10052005-9037s-projects.vercel.app",
                "https://lstfnd-frontend-iqwwq0g5u-akhilreddy10052005-9037s-projects.vercel.app",
                "https://lstfnd-frontend-*.vercel.app",
                "https://lstfnd-frontend.vercel.app",
                "http://localhost:[*]",
                "http://127.0.0.1:[*]"
        ));

        // Allowed Methods
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        
        // Allowed Headers (Adding more for safety)
        config.setAllowedHeaders(Arrays.asList(
                "Content-Type", 
                "Authorization", 
                "Accept", 
                "X-Requested-With", 
                "Origin", 
                "Access-Control-Request-Method", 
                "Access-Control-Request-Headers"
        ));
        
        // Exposed headers for the frontend
        config.setExposedHeaders(Arrays.asList("Authorization", "Content-Type", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}



