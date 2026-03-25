package com.smartcampus.api;

import com.smartcampus.model.Room;
import org.junit.jupiter.api.*;
import javax.ws.rs.core.Response;
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
}