package com.icon;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class Application extends ResourceConfig {

    public Application() {
        // Add a package used to scan for components.
        register(new ApplicationBinder());
        packages("com.icon.service");
    }
}
