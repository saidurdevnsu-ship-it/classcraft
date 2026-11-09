package com.classcraft.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

/**
 * Serves the built frontend (frontend/dist) and falls back to index.html for
 * client-side routes, so the whole app is available on http://localhost:8080.
 */
@Configuration
public class SpaWebConfig implements WebMvcConfigurer {

    @Value("${app.frontend.dist:file:../frontend/dist/}")
    private String frontendDist;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Spring never passes an empty path to resource resolvers, so map the root explicitly.
        registry.addViewController("/").setViewName("forward:/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(frontendDist)
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requested = location.createRelative(resourcePath);
                        if (!resourcePath.isEmpty() && requested.isFile() && requested.isReadable()) {
                            return requested;
                        }
                        if (resourcePath.startsWith("api/") || resourcePath.startsWith("swagger-ui")
                                || resourcePath.startsWith("api-docs")) {
                            return null;
                        }
                        Resource index = location.createRelative("index.html");
                        return index.exists() && index.isReadable() ? index : null;
                    }
                });
    }
}
