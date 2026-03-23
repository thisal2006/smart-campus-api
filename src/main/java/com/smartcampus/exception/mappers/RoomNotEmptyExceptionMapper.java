package com.smartcampus.exception.mappers;

import com.smartcampus.exception.RoomNotEmptyException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {

    @Override
    public Response toResponse(RoomNotEmptyException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Room Cannot Be Deleted");
        error.put("message", ex.getMessage());
        error.put("roomId", ex.getRoomId());
        error.put("activeSensors", ex.getSensorCount());
        error.put("status", 409);
        return Response.status(409).entity(error).build();
    }
}