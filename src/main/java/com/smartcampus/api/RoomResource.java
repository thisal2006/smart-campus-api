package com.smartcampus.api;

import com.smartcampus.model.Room;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Path("/rooms")
public class RoomResource {
    private static final ConcurrentHashMap<Integer, Room> roomStore = new ConcurrentHashMap<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRooms() {
        List<Room> rooms = new ArrayList<>(roomStore.values());
        return Response.ok(rooms).build();
    }
}