package com.smartcampus.api;

import com.smartcampus.model.Sensor;
import com.smartcampus.model.Room;
import com.smartcampus.exception.RoomNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;

@Path("/sensors")
public class SensorResource {
    private static final ConcurrentHashMap<Integer, Sensor> sensorStore = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Integer, Room> roomStore = RoomResource.getRoomStore();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSensor(Sensor sensor) {
        if (!roomStore.containsKey(sensor.getRoomId())) {
            throw new RoomNotFoundException(sensor.getRoomId());
        }
        Sensor newSensor = new Sensor(sensor.getName(), sensor.getType(), sensor.getRoomId());
        sensorStore.put(newSensor.getId(), newSensor);
        Room room = roomStore.get(sensor.getRoomId());
        room.addSensorId(newSensor.getId());
        return Response.created(URI.create("/api/v1/sensors/" + newSensor.getId()))
                .entity(newSensor)
                .build();
    }
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

public static ConcurrentHashMap<Integer, Sensor> getSensorStore() {
    return sensorStore;
}


@Path("/{sensorId}/readings")
public SensorReadingResource getSensorReadingResource(@PathParam("sensorId") int sensorId) {
    return new SensorReadingResource(sensorId);
}

@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response createSensor(Sensor sensor) {
    if (sensor.getName() == null || sensor.getName().trim().isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \"Sensor name cannot be empty\"}")
                .build();
    }
    if (sensor.getType() == null || sensor.getType().trim().isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \"Sensor type cannot be empty\"}")
                .build();
    }
    if (!roomStore.containsKey(sensor.getRoomId())) {
        throw new RoomNotFoundException(sensor.getRoomId());
    }
    Sensor newSensor = new Sensor(sensor.getName(), sensor.getType(), sensor.getRoomId());
    sensorStore.put(newSensor.getId(), newSensor);
    Room room = roomStore.get(sensor.getRoomId());
    room.addSensorId(newSensor.getId());
    return Response.created(URI.create("/api/v1/sensors/" + newSensor.getId()))
            .entity(newSensor)
            .build();
}

@PUT
@Path("/{sensorId}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response updateSensor(@PathParam("sensorId") int sensorId, Sensor updatedSensor) {
    Sensor existingSensor = sensorStore.get(sensorId);
    if (existingSensor == null) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"Sensor not found\"}")
                .build();
    }
    if (updatedSensor.getName() != null) existingSensor.setName(updatedSensor.getName());
    if (updatedSensor.getType() != null) existingSensor.setType(updatedSensor.getType());
    if (updatedSensor.getStatus() != null) existingSensor.setStatus(updatedSensor.getStatus());
    return Response.ok(existingSensor).build();
}

@DELETE
@Path("/{sensorId}")
@Produces(MediaType.APPLICATION_JSON)
public Response deleteSensor(@PathParam("sensorId") int sensorId) {
    Sensor sensor = sensorStore.get(sensorId);
    if (sensor == null) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"Sensor not found\"}")
                .build();
    }
    // Remove sensor from room's sensor list
    Room room = roomStore.get(sensor.getRoomId());
    if (room != null) {
        room.removeSensorId(sensorId);
    }
    sensorStore.remove(sensorId);
    return Response.noContent().build();
}