package com.cayuse.demo.models;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class City {

    private int id;
    private String zipCode;
    private String name;
    private String temp;
    private String timeZone;
    private String elevation;
    private String lat;
    private String lon;

    @java.beans.ConstructorProperties({"zipCode", "name", "temp", "lat", "lon"})
    public City(String zipCode, String name, String temp, String lat, String lon) {
        this.zipCode = zipCode;
        this.name = name;
        this.temp = temp;
        this.lat = lat;
        this.lon = lon;
    }
}
