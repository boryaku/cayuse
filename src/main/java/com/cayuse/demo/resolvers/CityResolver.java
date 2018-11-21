package com.cayuse.demo.resolvers;


import com.coxautodev.graphql.tools.GraphQLResolver;
import org.springframework.stereotype.Component;

@Component
public class CityResolver implements GraphQLResolver<City> {


    public String getTimeZone(City city){
        return "PST";
    }

    public String getElevation(City city){
        return "100 ft";
    }
}
