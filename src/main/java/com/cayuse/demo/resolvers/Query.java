package com.cayuse.demo.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
class Query implements GraphQLQueryResolver {

    public City getCity(String zipCode){

        return new City(zipCode, "Portland", "51");
    }
}


