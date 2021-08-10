package com.target.VolunteeringPlatform.Controller;

import com.target.VolunteeringPlatform.Service.LeaderService;
import com.target.VolunteeringPlatform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/account/leader")
public class LeaderController {

    @Autowired
    LeaderService leaderService;

    //Get endpoint to get list of all participants of an event from database
    @GetMapping(value = "/getTimeSpentByUsers")
    public Map getAllParticipants() {
        return leaderService.getTimeSpentByUser();
    }
}
