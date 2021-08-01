package com.target.VolunteeringPlatform.Controller;

import com.target.VolunteeringPlatform.PayloadResponse.MessageResponse;
import com.target.VolunteeringPlatform.Service.EventService;
import com.target.VolunteeringPlatform.Service.UserDetailsServiceImpl;
import com.target.VolunteeringPlatform.model.Event;
import com.target.VolunteeringPlatform.model.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/account/admin")
public class AdminController {
    @Autowired
    EventService eventService;

    @Autowired
    UserDetailsServiceImpl userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addEvents")
    public ResponseEntity<?> addEvent(@Valid @RequestBody Event event) {
        if (eventService.existsByName(event.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Event name already exists"));
        }
        System.out.println(event);
        eventService.addEvent(event);
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadFile/")
//                .path(event.getName())
//                .toUriString();
        return ResponseEntity.ok(new MessageResponse("Event Added Successfully"));
    }

    @DeleteMapping(value = "/events/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") int id) {
        if (!eventService.existsById(id)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Event Id doesn't exist"));
        }
        eventService.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Event Deleted Successfully"));
    }

    @PostMapping(value = "/updateEvents")
    public ResponseEntity<?> updateEvent(@Valid @RequestBody Event newEvent) {
        if (!eventService.existsById(newEvent.getEvent_id())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Event Id doesn't exist"));
        }
        eventService.addEvent(newEvent);
        return ResponseEntity.ok(new MessageResponse("Event Updated Successfully"));
    }

    @GetMapping(value = "/getAllParticipants/{event_name:[a-zA-Z &+-]*}")
    public List<User> getAllParticipants(@PathVariable("event_name") String event_name) {
        return eventService.getAllParticipants(event_name);
    }

    @PostMapping(value = "/sendReminders/{event_name:[a-zA-Z &+-]*}")
    public ResponseEntity<?> sendReminders(@PathVariable("event_name") String event_name) {
        if (!eventService.existsByName(event_name)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Event Id doesn't exist"));
        }
        eventService.sendReminders(event_name);
        return ResponseEntity.ok(new MessageResponse("Emails are sent successfully!"));
    }
}
