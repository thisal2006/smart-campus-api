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