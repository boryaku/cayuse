package com.cayuse.demo.repos;


import com.cayuse.demo.exceptions.RemoteException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GoogleElevationRepoIntegrationTests {

    @Autowired
    private ElevationRepo elevationRepo;

    @Test
    public void findByLatAndLon(){
        String elevation = elevationRepo.findByLatAndLon("45.45", "-122.64");
        assertThat("12.11576080322266").isEqualTo(elevation);
    }

    @Test(expected = RemoteException.class)
    public void findByLatAndLon_EXCEPTION(){
        elevationRepo.findByLatAndLon("abc", "-122.64");
    }

}