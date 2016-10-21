package com.clutteredcode.rest.servlet;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author cluttered.code@gmail.com
 */
public class ApiApplication extends ResourceConfig {

    public ApiApplication() {
        packages("com.clutteredcode.rest.resource");
        register(io.swagger.jaxrs.listing.ApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
    }
}