package com.cayuse.demo.repos;

import com.cayuse.demo.exceptions.RemoteException;
import com.cayuse.demo.models.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OpenWeatherCityRepoIntegrationTests {

    @Autowired
    private CityRepo cityRepo;


    @Test
    public void testFindByZipCode(){
        City city = cityRepo.findByZipCode("90210");
        assertThat(city).isNotNull();
        assertThat(city.getLat()).isEqualTo("34.07");
        assertThat(city.getLon()).isEqualTo("-118.4");
        assertThat(city.getName()).isEqualTo("Beverly Hills");
        assertThat(city.getTemp()).isNotEmpty();
    }

    @Test(expected = RemoteException.class)
    public void testFindByZipCode_EXCEPTION(){
        cityRepo.findByZipCode("123");
    }
}
