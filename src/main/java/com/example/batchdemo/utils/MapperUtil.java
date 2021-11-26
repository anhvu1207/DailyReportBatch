package com.example.batchdemo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class MapperUtil {
    public static Map<String, Object> toObjectMap(Object object) {
        ObjectMapper m = new ObjectMapper();
        return m.convertValue(object, Map.class);
    }
}
