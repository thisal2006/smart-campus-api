package com.smartcampus.exception;

public class SensorUnavailableException extends RuntimeException {
    private int sensorId;
    private String status;

    public SensorUnavailableException(int sensorId, String status) {
        super("Sensor " + sensorId + " is in " + status + " mode and cannot accept readings");
        this.sensorId = sensorId;
        this.status = status;
    }

    public int getSensorId() { return sensorId; }
    public String getStatus() { return status; }
}