package com.clutteredcode.rest.servlet;

import com.clutteredcode.rest.ApiModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.servlet.GuiceServletContextListener;

import javax.servlet.annotation.WebListener;

/**
 * @author cluttered.code@gmail.com
 */
@WebListener
public class GuiceContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(Stage.PRODUCTION, new ApiModule());
    }
}