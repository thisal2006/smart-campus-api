package com.smartcampus.exception;

public class RoomNotEmptyException extends RuntimeException {
    private int roomId;
    private int sensorCount;

    public RoomNotEmptyException(int roomId, int sensorCount) {
        super("Room " + roomId + " cannot be deleted because it has " + sensorCount + " active sensors");
        this.roomId = roomId;
        this.sensorCount = sensorCount;
    }

    public int getRoomId() { return roomId; }
    public int getSensorCount() { return sensorCount; }
}
