package com.smartcampus.api;

import com.smartcampus.model.Sensor;
import com.smartcampus.model.Reading;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;

public class SensorReadingResource {
    private static final ConcurrentHashMap<Integer, Sensor> sensorStore = SensorResource.getSensorStore();
    private int sensorId;

    public SensorReadingResource(int sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReadingHistory() {
        Sensor sensor = sensorStore.get(sensorId);
        if (sensor == null) {
            return Response.status(404).entity("{\"error\": \"Sensor not found\"}").build();
        }
        return Response.ok(sensor.getReadings()).build();
    }
}

@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response addReading(Reading reading) {
    Sensor sensor = sensorStore.get(sensorId);
    if (sensor == null) {
        return Response.status(404).entity("{\"error\": \"Sensor not found\"}").build();
    }
    if (!sensor.isAvailable()) {
        throw new SensorUnavailableException(sensorId, sensor.getStatus());
    }
    sensor.addReading(reading);
    return Response.status(Response.Status.CREATED).entity(reading).build();
}