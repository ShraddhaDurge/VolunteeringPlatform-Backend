package com.target.VolunteeringPlatform.Controller;

import com.target.VolunteeringPlatform.PayloadResponse.MessageResponse;
import com.target.VolunteeringPlatform.Service.EventService;
import com.target.VolunteeringPlatform.Service.LeaderService;
import com.target.VolunteeringPlatform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/account/leader")
public class LeaderController {

    @Autowired
    LeaderService leaderService;

    @Autowired
    EventService eventService;

    @Autowired
    UserService userService;

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

    //Get endpoint for user analytics data
    @GetMapping(value = "/userAnalyticsCounts")
    public List<Integer> userAnalyticsCounts() {
        return leaderService.userAnalyticsCounts();
    }

    //Send nomination card to user for an event
    @PostMapping(value = "/sendNominationCard/{user_id}/{event_name:[a-zA-Z &+-]*}")
    public ResponseEntity<?> sendNominationCard(@PathVariable("user_id") int user_id, @PathVariable("event_name") String event_name) {
        //check if event exists in database
        if (!eventService.existsByName(event_name)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Event Name doesn't exist"));
        }
        //check if user exists in database
        if (!userService.existsById(user_id)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("User doesn't exist"));
        }

        leaderService.sendNominationCard(user_id, event_name);         //send certificates mails
        return ResponseEntity.ok(new MessageResponse("Nomination card was sent successfully!"));
    }
}
