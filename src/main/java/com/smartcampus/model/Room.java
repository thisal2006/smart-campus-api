package com.smartcampus.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Room {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);
    private int id;
    private String name;
    private String building;
    private int floor;
    private List<Integer> sensorIds;

    public Room() {
        this.sensorIds = new ArrayList<>();
    }

    public Room(String name, String building, int floor) {
        this.id = idGenerator.getAndIncrement();
        this.name = name;
        this.building = building;
        this.floor = floor;
        this.sensorIds = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBuilding() { return building; }
    public void setBuilding(String building) { this.building = building; }
    public int getFloor() { return floor; }
    public void setFloor(int floor) { this.floor = floor; }
    public List<Integer> getSensorIds() { return sensorIds; }
    public void setSensorIds(List<Integer> sensorIds) { this.sensorIds = sensorIds; }
    public void addSensorId(int sensorId) { this.sensorIds.add(sensorId); }
    public boolean hasSensors() { return !sensorIds.isEmpty(); }
}