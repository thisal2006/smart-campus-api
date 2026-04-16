package main.java.com.smartcampus.model;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Sensor {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);
    
    private int id;
    private String name;
    private String type;  // CO2, TEMP, OCCUPANCY, LIGHTING
    private int roomId;
    private String status;  // ACTIVE, MAINTENANCE, OFFLINE
    private String currentValue;
    private List<Reading> readings;
    
    public Sensor() {andrpi
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
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getCurrentValue() { return currentValue; }
    public void setCurrentValue(String currentValue) { this.currentValue = currentValue; }
    
    public List<Reading> getReadings() { return readings; }
    public void setReadings(List<Reading> readings) { this.readings = readings; }
    
    public void addReading(Reading reading) {
        this.readings.add(reading);
        this.currentValue = reading.getValue();
    }
    
    public boolean isAvailable() {
        return !"MAINTENANCE".equalsIgnoreCase(this.status);
    }
}