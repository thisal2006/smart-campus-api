package com.smartcampus.api;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

public class Main {

    /**
     * Loads server configuration from config.properties (if the file exists)
     */
    private static void loadConfig() {
        try (InputStream input = Main.class.getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                System.out.println("ℹ️  config.properties not found → using default port 8080");
                return;
            }

            Properties prop = new Properties();
            prop.load(input);
            String port = prop.getProperty("server.port", "8080");
            System.setProperty("server.port", port);
            System.out.println("✅ Loaded config: server.port = " + port);

        } catch (IOException e) {
            System.err.println("⚠️ Config file not found or error reading it → using defaults");
        }
    }

    public static void main(String[] args) throws Exception {
        // Load configuration first
        loadConfig();

        // Global uncaught exception handler (optional but nice)
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            System.err.println("Uncaught exception in thread " + thread.getName());
            throwable.printStackTrace();
        });

        String port = System.getProperty("server.port", "8080");

        // Start the embedded Grizzly server with our JAX-RS application
        ResourceConfig config = new ResourceConfig(SmartCampusApplication.class);

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
                URI.create("http://localhost:" + port + "/")
        );

        System.out.println("🚀 Smart Campus API started successfully!");
        System.out.println("📍 URL: http://localhost:" + port + "/api/v1");
        System.out.println("Press Enter to stop the server...");

        System.in.read();
        server.shutdownNow();
    }
}