package com.cayuse.demo.resolvers;

public class City {

    protected int id;
    protected String zipCode;
    protected String name;
    protected String temp;
    protected String timeZone;
    protected String elevation;

    public City(String zipCode, String name, String temp) {
        this.zipCode = zipCode;
        this.name = name;
        this.temp = temp;
    }
}
