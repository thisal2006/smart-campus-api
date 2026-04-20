package com.smartcampus.api;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import java.net.URI;

public class Main {
    public static void main(String[] args) {
        try {
            // Create resource config
            org.glassfish.jersey.server.ResourceConfig rc = new org.glassfish.jersey.server.ResourceConfig();
            rc.packages("com.smartcampus.api");
            rc.register(JacksonFeature.class);
            
            // Start server
            HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
                URI.create("http://localhost:8080/"), 
                rc
            );
            
            System.out.println("========================================");
            System.out.println("JAX-RS Smart Campus API Server Started!");
            System.out.println("========================================");
            System.out.println("Base URL: http://localhost:8080/api/v1");
            System.out.println("Discovery: http://localhost:8080/api/v1/");
            System.out.println("Rooms: http://localhost:8080/api/v1/rooms");
            System.out.println("Sensors: http://localhost:8080/api/v1/sensors");
            System.out.println("========================================");
            System.out.println("Press ENTER to stop the server...");
            System.in.read();
            server.shutdownNow();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



