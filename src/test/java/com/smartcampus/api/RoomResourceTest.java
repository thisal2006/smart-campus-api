package com.smartcampus.api;

import com.smartcampus.model.Room;
import org.junit.jupiter.api.*;
import jakarta.ws.rs.core.Response;
import static org.junit.jupiter.api.Assertions.*;

public class RoomResourceTest {
    private static RoomResource roomResource;

    @BeforeAll
    static void setUp() {
        roomResource = new RoomResource();
    }

    @Test
    void testCreateRoom() {
        Room room = new Room("Test Lab", "Science", 2);
        Response response = roomResource.createRoom(room);
        assertEquals(201, response.getStatus());
    }

    @Test
    void testGetAllRooms() {
        Response response = roomResource.getAllRooms();
        assertEquals(200, response.getStatus());
    }

    @Test
    void testDeleteRoomWithSensors() {
        // Create room first
        Room room = new Room("Occupied Room", "Main", 1);
        Response createResponse = roomResource.createRoom(room);
        Room createdRoom = (Room) createResponse.getEntity();

        // Add sensor to room (simulate)
        createdRoom.addSensorId(999);

        // Try to delete - should fail
        Response deleteResponse = roomResource.deleteRoom(createdRoom.getId());
        assertEquals(409, deleteResponse.getStatus());
    }
}

