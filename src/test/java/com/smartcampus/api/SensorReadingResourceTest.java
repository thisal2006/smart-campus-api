package com.smartcampus.api;

import com.smartcampus.model.Reading;
import org.junit.jupiter.api.*;
import javax.ws.rs.core.Response;
import static org.junit.jupiter.api.Assertions.*;

public class SensorReadingResourceTest {

    @Test
    void testAddReadingToSensor() {
        // Setup sensor first
        // Then add reading
        Reading reading = new Reading("450", "ppm");
        assertNotNull(reading);
    }

    @Test
    void testGetReadingHistory() {
        // Test retrieving reading history
        Response response = null;
        // Assert history is returned
    }
}