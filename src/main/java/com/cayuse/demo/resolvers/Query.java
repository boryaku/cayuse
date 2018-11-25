package com.cayuse.demo.resolvers;

import com.cayuse.demo.models.City;
import com.cayuse.demo.repos.CityRepo;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
class Query implements GraphQLQueryResolver {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CityRepo cityRepo;


    /**
     * Root query for the top level city info finder.  We need to resolve coordinates here for the other services
     * to use for their look ups ie. elevation and timezone
     *
     * @param zipCode
     *
     * @return partially hydrated City
     */
    public City getCity(String zipCode){
        logger.debug("query request for zip code {}", zipCode);
        return cityRepo.findByZipCode(zipCode);
    }
}


