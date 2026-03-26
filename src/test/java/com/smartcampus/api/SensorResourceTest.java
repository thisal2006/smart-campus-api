package com.smartcampus.api;

import com.smartcampus.model.Sensor;
import org.junit.jupiter.api.*;
import javax.ws.rs.core.Response;
import static org.junit.jupiter.api.Assertions.*;

public class SensorResourceTest {
    private static SensorResource sensorResource;
    private static RoomResource roomResource;

    @BeforeAll
    static void setUp() {
        sensorResource = new SensorResource();
        roomResource = new RoomResource();
    }

    @Test
    void testCreateSensorWithValidRoom() {
        // Create room first
        Room room = new Room("Sensor Room", "Tech", 3);
        Response roomResponse = roomResource.createRoom(room);
        Room createdRoom = (Room) roomResponse.getEntity();

        // Create sensor in that room
        Sensor sensor = new Sensor("Temp Sensor", "TEMP", createdRoom.getId());
        Response sensorResponse = sensorResource.createSensor(sensor);
        assertEquals(201, sensorResponse.getStatus());
    }
    @Test
    void testCreateSensorWithInvalidRoom() {
        Sensor sensor = new Sensor("Invalid Sensor", "CO2", 99999);
        Response response = sensorResource.createSensor(sensor);
        assertEquals(422, response.getStatus());
    }
}