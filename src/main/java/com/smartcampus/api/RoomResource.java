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

@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response createRoom(Room room) {
    if (room.getName() == null || room.getName().trim().isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \"Room name cannot be empty\"}")
                .build();
    }
    Room newRoom = new Room(room.getName(), room.getBuilding(), room.getFloor());
    roomStore.put(newRoom.getId(), newRoom);
    return Response.status(Response.Status.CREATED)
            .entity(newRoom)
            .build();
}

@GET
@Path("/{roomId}")
@Produces(MediaType.APPLICATION_JSON)
public Response getRoomById(@PathParam("roomId") int roomId) {
    Room room = roomStore.get(roomId);
    if (room == null) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"Room not found with id: " + roomId + "\"}")
                .build();
    }
    return Response.ok(room).build();
}

@DELETE
@Path("/{roomId}")
@Produces(MediaType.APPLICATION_JSON)
public Response deleteRoom(@PathParam("roomId") int roomId) {
    Room room = roomStore.get(roomId);
    if (room == null) {
        throw new javax.ws.rs.NotFoundException("Room not found: " + roomId);
    }
    if (room.hasSensors()) {
        throw new RoomNotEmptyException(roomId, room.getSensorIds().size());
    }
    roomStore.remove(roomId);
    return Response.noContent().build();
}