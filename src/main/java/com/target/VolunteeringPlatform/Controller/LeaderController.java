package com.target.VolunteeringPlatform.Controller;

import com.target.VolunteeringPlatform.PayloadResponse.MessageResponse;
import com.target.VolunteeringPlatform.Service.EventService;
import com.target.VolunteeringPlatform.Service.LeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/account/leader")
public class LeaderController {

    @Autowired
    LeaderService leaderService;

    @Autowired
    EventService eventService;

    //Get endpoint
    @GetMapping(value = "/getTimeSpentByUsers")
    public Map getTimeSpent() {
        return leaderService.getTimeSpentByUser();
    }

    //Send certificates to all participants of an event
    @PostMapping(value = "/sendCertificates/{event_id}")
    public ResponseEntity<?> sendCertificates(@PathVariable("event_id") int event_id) {
        //check if event exists in database
        if (!eventService.existsById(event_id)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Event Id doesn't exist"));
        }
        leaderService.sendCertificates(event_id);         //send certificates mails
        return ResponseEntity.ok(new MessageResponse("Certificates are sent successfully!"));
    }

    //Get endpoint
    @GetMapping(value = "/userAnalyticsCounts")
    public Map<String, Integer> userAnalyticsCounts() {
        return leaderService.userAnalyticsCounts();
    }

}
