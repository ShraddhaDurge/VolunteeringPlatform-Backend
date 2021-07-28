package com.target.VolunteeringPlatform.Controller;

import com.target.VolunteeringPlatform.DAO.EventRepository;
import com.target.VolunteeringPlatform.DAO.UserRepository;
import com.target.VolunteeringPlatform.RequestResponse.EventParticipatedResponse;
import com.target.VolunteeringPlatform.RequestResponse.MessageResponse;
import com.target.VolunteeringPlatform.RequestResponse.SignupRequest;
import com.target.VolunteeringPlatform.Service.EventService;
import com.target.VolunteeringPlatform.model.Event;
import com.target.VolunteeringPlatform.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/account/events")
public class EventController {
    @Autowired
    EventService eventService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;

    @GetMapping(value = "/{id}")
    public Event getEventById(@PathVariable("id") int id) {
        return eventService.getById(id);
    }

    @GetMapping(value = "/allEvents")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping(value = "/pastEvents")
    public List<Event> getPastEvents() {
        return eventService.getPastEvents();
    }

    @PostMapping("/registerForEvent/{eventId}/{userId}")
    public ResponseEntity<?> registerForEvent(@PathVariable("eventId") int eventId,@PathVariable("userId") int userId) {
        User user = userRepository.findById(userId);
        if(user == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("No such user exists"));
        }

        Event event = eventRepository.getById(eventId);
        Set<Event> events = user.getEvents();

        if(events.contains(event)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("User already registered for the event"));
        }

        eventService.registerUserForEvent(eventId,userId);
        return ResponseEntity.ok(new MessageResponse("User Registered Successfully"));
    }

    @GetMapping(value = "/getEventsList/{isFutureEvent}/{eventType:[a-zA-Z &+-]*}")
    public List<Event> getEventsList(@PathVariable Boolean isFutureEvent, @PathVariable String eventType) {
        return eventService.getEvents(isFutureEvent, eventType);
    }

    @GetMapping(value = "/getEventParticipated/{id}")
    public EventParticipatedResponse getEventParticipated(@PathVariable("id") int userId) {
        return eventService.getEventsParticipated(userId);
    }
}
