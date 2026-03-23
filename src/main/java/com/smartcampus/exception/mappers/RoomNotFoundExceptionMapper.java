package com.smartcampus.exception.mappers;

import com.smartcampus.exception.RoomNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class RoomNotFoundExceptionMapper implements ExceptionMapper<RoomNotFoundException> {

    @Override
    public Response toResponse(RoomNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Invalid Room Reference");
        error.put("message", ex.getMessage());
        error.put("roomId", ex.getRoomId());
        error.put("status", 422);
        return Response.status(422).entity(error).build();
    }
}