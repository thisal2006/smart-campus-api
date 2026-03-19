package com.smartcampus.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reading {
    private String timestamp;
    private String value;
    private String unit;

    public Reading() {
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public Reading(String value, String unit) {
        this();
        this.value = value;
        this.unit = unit;
    }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
}