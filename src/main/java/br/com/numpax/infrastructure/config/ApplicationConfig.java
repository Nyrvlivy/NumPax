package br.com.numpax.infrastructure.config;

import org.glassfish.jersey.server.ResourceConfig;

public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        packages("br.com.numpax.API.V1.controllers");
    }
}
