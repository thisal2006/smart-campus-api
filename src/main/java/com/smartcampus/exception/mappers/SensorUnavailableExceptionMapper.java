package com.smartcampus.exception.mappers;

import com.smartcampus.exception.SensorUnavailableException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException> {

    @Override
    public Response toResponse(SensorUnavailableException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Sensor Not Available");
        error.put("message", ex.getMessage());
        error.put("sensorId", ex.getSensorId());
        error.put("currentStatus", ex.getStatus());
        error.put("status", 403);
        return Response.status(403).entity(error).build();
    }
}