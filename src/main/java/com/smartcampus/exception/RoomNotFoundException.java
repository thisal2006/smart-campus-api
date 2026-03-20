package com.smartcampus.exception;

public class RoomNotFoundException extends RuntimeException {
    private int roomId;

    public RoomNotFoundException(int roomId) {
        super("Room with ID " + roomId + " does not exist");
        this.roomId = roomId;
    }

    public int getRoomId() { return roomId; }
}