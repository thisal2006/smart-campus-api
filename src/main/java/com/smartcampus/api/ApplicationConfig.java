package com.smartcampus.api;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api/v1")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(DiscoveryResource.class);
        classes.add(RoomResource.class);
        classes.add(SensorResource.class);
        classes.add(SensorReadingResource.class);
        classes.add(com.smartcampus.exception.mappers.RoomNotEmptyExceptionMapper.class);
        classes.add(com.smartcampus.exception.mappers.RoomNotFoundExceptionMapper.class);
        classes.add(com.smartcampus.exception.mappers.SensorUnavailableExceptionMapper.class);
        classes.add(com.smartcampus.exception.mappers.GlobalExceptionMapper.class);
        classes.add(com.smartcampus.filter.LoggingFilter.class);
        return classes;
    }
}