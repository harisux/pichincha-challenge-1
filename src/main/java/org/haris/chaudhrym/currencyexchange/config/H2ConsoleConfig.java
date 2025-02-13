package org.haris.chaudhrym.currencyexchange.config;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

// This configuration class is used to enable h2 console in port 8087

@Configuration
public class H2ConsoleConfig {
    
    private Server webServer;

    private final String WEB_PORT = "8087";

    @EventListener(ApplicationStartedEvent.class)
    public void start() throws SQLException {
        this.webServer = Server.createWebServer("-webPort", WEB_PORT).start();
    }

    @EventListener(ContextClosedEvent.class)
    public void stop() {
        this.webServer.stop();
    }

}
