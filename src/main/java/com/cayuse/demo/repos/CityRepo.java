package com.cayuse.demo.repos;

import com.cayuse.demo.models.City;

public interface CityRepo {

    public City findByZipCode(String zipCode);

}
