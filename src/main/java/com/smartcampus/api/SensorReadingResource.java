package com.smartcampus.api;

import com.smartcampus.model.Sensor;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class SensorReadingResource {
    private int sensorId;
    private static Map<Integer, List<Map<String, String>>> allReadings = new HashMap<>();

    public SensorReadingResource(int sensorId) {
        this.sensorId = sensorId;
        if (!allReadings.containsKey(sensorId)) {
            allReadings.put(sensorId, new ArrayList<>());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReadings() {
        return Response.ok(allReadings.get(sensorId)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(Map<String, String> reading) {
        Sensor sensor = SensorResource.getSensorStore().get(sensorId);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Sensor not found\"}")
                    .build();
        }
        if (!sensor.isAvailable()) {
            throw new SensorUnavailableException("Sensor is in MAINTENANCE mode and cannot accept new readings");
        }

        allReadings.get(sensorId).add(reading);

        // Side-effect: update parent Sensor's currentValue
        if (reading.containsKey("value")) {
            sensor.setCurrentValue(reading.get("value"));
        }

        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}