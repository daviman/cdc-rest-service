package com.sorint.demo.service.model;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.Serializable;

public class CountRecord implements Serializable {

    private String name;

    private int count;

    public CountRecord(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String toJsonString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
            return json;
        } catch(Exception e) {
            return e.getLocalizedMessage();
        }
    }

}
