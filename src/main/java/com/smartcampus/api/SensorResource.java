package com.smartcampus.api;

import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Path("/sensors")
public class SensorResource {
    private static final ConcurrentHashMap<Integer, Sensor> sensorStore = new ConcurrentHashMap<>();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSensor(Sensor sensor) {
        if (!RoomResource.getRoomStore().containsKey(sensor.getRoomId())) {
            throw new LinkedResourceNotFoundException("Room not found: " + sensor.getRoomId());
        }

        Sensor newSensor = new Sensor(sensor.getName(), sensor.getType(), sensor.getRoomId());
        sensorStore.put(newSensor.getId(), newSensor);

        Room room = RoomResource.getRoomStore().get(sensor.getRoomId());
        if (room != null) {
            room.addSensorId(newSensor.getId());
        }

        return Response.created(URI.create("/api/v1/sensors/" + newSensor.getId()))
                .entity(newSensor)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSensors(@QueryParam("type") String type) {
        List<Sensor> sensors = new ArrayList<>(sensorStore.values());
        if (type != null && !type.isEmpty()) {
            sensors = sensors.stream()
                    .filter(s -> s.getType().equalsIgnoreCase(type))
                    .collect(Collectors.toList());
        }
        return Response.ok(sensors).build();
    }

    @GET
    @Path("/{sensorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSensorById(@PathParam("sensorId") int sensorId) {
        Sensor sensor = sensorStore.get(sensorId);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new com.smartcampus.model.ErrorResponse("Not Found", "Sensor not found", 404))
                    .build();
        }
        return Response.ok(sensor).build();
    }

    @Path("/{sensorId}/readings")
    public SensorReadingResource getSensorReadingResource(@PathParam("sensorId") int sensorId) {
        return new SensorReadingResource(sensorId);
    }

    public static ConcurrentHashMap<Integer, Sensor> getSensorStore() {
        return sensorStore;
    }
}