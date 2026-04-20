package com.smartcampus.api;

import com.smartcampus.model.Room;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
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

    @GET
    @Path("/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomById(@PathParam("roomId") int roomId) {
        Room room = roomStore.get(roomId);
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Room not found\"}")
                    .build();
        }
        return Response.ok(room).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoom(Room room) {
        Room newRoom = new Room(room.getName(), room.getBuilding(), room.getFloor());
        roomStore.put(newRoom.getId(), newRoom);
        return Response.created(URI.create("/api/v1/rooms/" + newRoom.getId()))
                .entity(newRoom)
                .build();
    }

    @DELETE
    @Path("/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRoom(@PathParam("roomId") int roomId) {
        Room room = roomStore.get(roomId);
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Room not found\"}")
                    .build();
        }
        if (room.hasSensors()) {
            throw new RoomNotEmptyException("Cannot delete room with active sensors: " + roomId);
        }
        roomStore.remove(roomId);
        return Response.noContent().build();
    }

    public static ConcurrentHashMap<Integer, Room> getRoomStore() {
        return roomStore;
    }
}