package com.cayuse.demo.resolvers;

import com.cayuse.demo.models.City;
import com.cayuse.demo.repos.ElevationRepo;
import com.cayuse.demo.repos.TimezoneRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityResolverUnitTests {

    @MockBean
    private TimezoneRepo timezoneRepo;

    @MockBean
    private ElevationRepo elevationRepo;

    @Autowired
    private CityResolver cityResolver;

    @Test
    public void getTimeZone(){
        City city = new City("90210", "Beverly Hills", "100", "34.07", "-118.4");

        cityResolver.getTimeZone(city);

        Mockito.verify(this.timezoneRepo, Mockito.times(1)).findByLatAndLon("34.07", "-118.4");
    }

    @Test
    public void getElevation(){
        City city = new City("90210", "Beverly Hills", "100", "34.07", "-118.4");

        cityResolver.getElevation(city);

        Mockito.verify(this.elevationRepo, Mockito.times(1)).findByLatAndLon("34.07", "-118.4");
    }
}
