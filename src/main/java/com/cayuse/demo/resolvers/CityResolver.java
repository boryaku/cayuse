package com.cayuse.demo.resolvers;

import com.cayuse.demo.models.City;
import com.cayuse.demo.repos.ElevationRepo;
import com.cayuse.demo.repos.TimezoneRepo;
import com.coxautodev.graphql.tools.GraphQLResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * This is the City Resolver it's the READ interface for data, we don't define any Mutations which would provide
 * WRITE access.  This following the interface segregation pattern as described in the SOLID paradigm.  This is
 * comparable to a traditional "Service" component.
 */
@Component
public class CityResolver implements GraphQLResolver<City> {


    @Autowired
    private TimezoneRepo timezoneRepo;


    @Autowired
    private ElevationRepo elevationRepo;

    /**
     * Resolve the timezone using the city's lat/lon.
     *
     * NOTE: This is only called if the field is requested.
     * @param city
     * @return
     */
    public String getTimeZone(City city){
        return timezoneRepo.findByLatAndLon(city.getLat(), city.getLon());
    }


    /**
     * Resolve the elevation using the city's lat/lon.
     *
     * NOTE: This is only called if the field is requested.
     * @param city
     * @return
     */
    public String getElevation(City city){
        return elevationRepo.findByLatAndLon(city.getLat(), city.getLon());
    }
}
