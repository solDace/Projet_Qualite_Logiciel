package ca.ulaval.glo4002.game;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameServer implements Runnable {
    private static final int PORT = 8181;
    private final Logger logger = LoggerFactory.getLogger(GameServer.class);

    public static void main(String[] args) {
        new GameServer().run();
    }

    public void run() {

        Server server = new Server(PORT);
        ServletContextHandler contextHandler = new ServletContextHandler(server, "/");
        ResourceConfig packageConfig = new ResourceConfig()
            .packages("ca.ulaval.glo4002.game")
            .register(ConfigurationGameServer.class)
            .register(JacksonFeature.withoutExceptionMappers());
        ServletContainer container = new ServletContainer(packageConfig);
        ServletHolder servletHolder = new ServletHolder(container);

        contextHandler.addServlet(servletHolder, "/*");

        try {
            server.start();
            logger.info("Server started on port " + PORT);
            server.join();
        } catch (Exception e) {
            logger.error("Error starting server", e);
        } finally {
            if (server.isRunning()) {
                server.destroy();
            }
        }
    }

}
