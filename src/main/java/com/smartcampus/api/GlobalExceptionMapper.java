package com.smartcampus.api;

import com.smartcampus.model.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable ex) {
        ex.printStackTrace(); // server log only
        ErrorResponse error = new ErrorResponse("Internal Server Error", "An unexpected error occurred. Please try again later.", 500);
        return Response.status(500).entity(error).build();
    }
}