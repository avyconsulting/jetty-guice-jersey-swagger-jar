package com.clutteredcode.rest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author cluttered.code@gmail.com
 */
public class ApiServer implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ApiServer.class);

    private final int port;
    private final Server server;

    public ApiServer(final int port) {
        this.port = port;

        String resourceBase = "src/main/webapp";
        if (Files.notExists(Paths.get(resourceBase))) {
            resourceBase = ApiServer.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm();
        }

        final String xmlDescriptor = ApiServer.class.getResource("/WEB-INF/web.xml").toExternalForm();

        final WebAppContext webAppContext = new WebAppContext(resourceBase, "/");
        webAppContext.setDescriptor(xmlDescriptor);

        server = new Server(port);
        server.setHandler(webAppContext);
    }

    @Override
    public void run() {
        try {
            server.start();
            LOG.info("Server listening on localhost:{}", port);
            server.join();
        } catch (final InterruptedException ie) {
            LOG.warn("Server was Interrupted");
        } catch (final Exception e) {
            LOG.error("Server was unable to start", e);
        }
    }

    public void stop() throws Exception {
        LOG.info("Stopping Server");
        server.stop();
    }

    public static void main(final String[] args) {
        final ApiServer server = new ApiServer(8080);
        server.run();
    }
}
