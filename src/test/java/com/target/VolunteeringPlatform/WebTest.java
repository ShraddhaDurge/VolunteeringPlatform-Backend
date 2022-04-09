package com.target.VolunteeringPlatform;

import com.target.VolunteeringPlatform.Controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WebTest {

    @Autowired
    UserController userController;

    @Test
    void contextLoads() {
        assert userController!=null;
    }
}
