package com.cayuse.demo.resolvers;

public class City {

    protected int id;
    protected String zipCode;
    protected String name;
    protected String temp;
    protected String timeZone;
    protected String elevation;
    protected String lat;
    protected String lon;

    public City(String zipCode, String name, String temp, String lat, String lon) {
        this.zipCode = zipCode;
        this.name = name;
        this.temp = temp;
        this.lat = lat;
        this.lon = lon;
    }
}
