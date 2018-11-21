package com.cayuse.demo.repos;

public interface TimezoneRepo {

    public String findByLatAndLon(String lat, String lon);
}
