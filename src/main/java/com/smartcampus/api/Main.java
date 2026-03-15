package com.smartcampus.api;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import java.net.URI;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
                URI.create("http://localhost:8080/"),
                new ApplicationConfig()
        );
        System.out.println("Server started at http://localhost:8080/api/v1");
        System.out.println("Press Enter to stop...");
        System.in.read();
        server.shutdown();
    }
}