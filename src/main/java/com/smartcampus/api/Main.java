package com.smartcampus.api;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import java.net.URI;

public static void main(String[] args) throws Exception {
    String port = System.getProperty("server.port", "8080");
    HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
            URI.create("http://localhost:" + port + "/"),
            new ApplicationConfig()
    );
    System.out.println("Server started at http://localhost:" + port + "/api/v1");
    System.in.read();
    server.shutdown();
}