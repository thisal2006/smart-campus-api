package com.smartcampus.api;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import java.net.URI;

public static void main(String[] args) throws Exception {
    Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
        System.err.println("Uncaught exception in thread " + thread.getName());
        throwable.printStackTrace();
    });
    String port = System.getProperty("server.port", "8080");
    HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
            URI.create("http://localhost:" + port + "/"),
            new ApplicationConfig()
    );
    System.out.println("Server started at http://localhost:" + port + "/api/v1");
    System.in.read();
    server.shutdown();
}