package com.clutteredcode.rest;

import com.clutteredcode.rest.filter.CrossOriginResourceSharingFilter;
import com.clutteredcode.rest.servlet.SwaggerBootstrap;
import com.clutteredcode.rest.servlet.ApiApplication;
import com.google.inject.servlet.ServletModule;
import io.swagger.jaxrs.config.BeanConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cluttered.code@gmail.com
 */
public class ApiModule extends ServletModule {

    @Override
    protected void configureServlets() {
        bind(ServletContainer.class).asEagerSingleton();
        bind(CrossOriginResourceSharingFilter.class).asEagerSingleton();

        initializeApplicationServlet();
        initializeSwaggerBootstrap();

        // CORS Filter
        filter("/*").through(CrossOriginResourceSharingFilter.class);
    }

    private void initializeApplicationServlet() {
        final Map<String, String> props = new HashMap<>();
        props.put("javax.ws.rs.Application", ApiApplication.class.getName());
        props.put("jersey.config.server.wadl.disableWadl", "true");
        serve("/api/*").with(ServletContainer.class, props);
    }

    private void initializeSwaggerBootstrap() {
        serve("").with(SwaggerBootstrap.class);
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0-SNAPSHOT");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setTitle("Todo API");
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/api");
        beanConfig.setResourcePackage("com.clutteredcode.rest.resource");
        beanConfig.setScan(true);
    }
}