package com.cayuse.demo.repos;

import com.cayuse.demo.exceptions.RemoteException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GoogleTimezoneRepoIntegrationTests {

    @Autowired
    private TimezoneRepo timezoneRepo;

    @Test
    public void findByLatAndLon(){
        String timezone = timezoneRepo.findByLatAndLon("45.45", "-122.64");
        assertThat("Pacific Standard Time").isEqualTo(timezone);
    }

    @Test(expected = RemoteException.class)
    public void findByLatAndLon_EXCEPTION(){
        timezoneRepo.findByLatAndLon("abc", "-122.64");
    }
}
