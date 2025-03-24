package com.oop.cw.shenon3.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * Configuration class for setting up Cross-Origin Resource Sharing (CORS) rules in the application.
 * This class enables CORS for specific API endpoints to allow communication between the server and
 * front-end applications hosted on different domains (e.g., during development).
 **/
 @Configuration
public class Webconfig implements WebMvcConfigurer {

    /**Applies to all endpoints under the `/api/**` path.
     * - Allows origins from `http://localhost:3000` and `http://localhost:3001` (commonly used during development).
     * - Permits HTTP methods: GET, POST, PUT, DELETE, and OPTIONS.
     * - Accepts all headers (`*`) in requests.
     * - Allows credentials (e.g., cookies, HTTP authentication) to be included in cross-origin requests.
     * **/

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000","http://localhost:3001")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

}
