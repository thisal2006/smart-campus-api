package com.smartcampus.api;

import com.smartcampus.model.Room;
import com.smartcampus.exception.RoomNotEmptyException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Path("/rooms")
public class RoomResource {

    // Thread-safe in-memory store (required for report explanation on concurrency)
    private static final ConcurrentHashMap<Integer, Room> roomStore = new ConcurrentHashMap<>();

    // Default rooms as requested
    static {
        Room defaultRoom = new Room("Main Auditorium", "Central", 1);
        roomStore.put(defaultRoom.getId(), defaultRoom);

        Room labRoom = new Room("CS Lab 101", "Engineering", 2);
        roomStore.put(labRoom.getId(), labRoom);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRooms() {
        List<Room> rooms = new ArrayList<>(roomStore.values());
        return Response.ok(rooms).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoom(Room room) {
        // Validation
        if (room.getName() == null || room.getName().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Room name cannot be empty\"}")
                    .build();
        }
        if (room.getBuilding() == null || room.getBuilding().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Building name cannot be empty\"}")
                    .build();
        }
        if (room.getFloor() < 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Floor number must be 0 or greater\"}")
                    .build();
        }

        Room newRoom = new Room(room.getName(), room.getBuilding(), room.getFloor());
        roomStore.put(newRoom.getId(), newRoom);

        return Response.status(Response.Status.CREATED)
                .entity(newRoom)
                .header("Location", "/api/v1/rooms/" + newRoom.getId())
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
    public Response deleteRoom(@PathParam("roomId") int roomId) {
        Room room = roomStore.get(roomId);
        if (room == null) {
            throw new NotFoundException("Room not found: " + roomId);
        }
        if (room.hasSensors()) {
            throw new RoomNotEmptyException(roomId, room.getSensorIds().size());
        }
        roomStore.remove(roomId);
        return Response.noContent().build();
    }
}