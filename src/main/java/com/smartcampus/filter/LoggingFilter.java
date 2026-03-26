package com.smartcampus.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private static final Logger logger = Logger.getLogger(LoggingFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        int responseSize = responseContext.getEntity() != null ?
                responseContext.getEntity().toString().length() : 0;
        logger.info("RESPONSE: " + responseContext.getStatus() + " | Size: " + responseSize + " bytes");
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        logger.info("RESPONSE: " + responseContext.getStatus());
    }
}