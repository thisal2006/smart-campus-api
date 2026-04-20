package com.smartcampus.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Sensor {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);
    private int id;
    private String name;
    private String type;
    private int roomId;
    private String status;
    private String currentValue;
    private List<Reading> readings;
    
    public Sensor() {
        this.readings = new ArrayList<>();
        this.status = "ACTIVE";
        this.currentValue = "0";
    }
    
    public Sensor(String name, String type, int roomId) {
        this.id = idGenerator.getAndIncrement();
        this.name = name;
        this.type = type;
        this.roomId = roomId;
        this.status = "ACTIVE";
        this.currentValue = "0";
        this.readings = new ArrayList<>();
    }
    
    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public int getRoomId() { return roomId; }
    public String getStatus() { return status; }
    public String getCurrentValue() { return currentValue; }
    public List<Reading> getReadings() { return readings; }
    
    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public void setStatus(String status) { this.status = status; }
    public void setCurrentValue(String currentValue) { this.currentValue = currentValue; }
    public void setReadings(List<Reading> readings) { this.readings = readings; }
    
    // Helper methods
    public void addReading(Reading reading) {
        this.readings.add(reading);
        this.currentValue = reading.getValue();
    }
    public boolean isAvailable() { return !"MAINTENANCE".equalsIgnoreCase(this.status); }
}



