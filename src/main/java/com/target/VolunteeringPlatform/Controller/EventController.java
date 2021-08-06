package com.target.VolunteeringPlatform.Controller;

import com.target.VolunteeringPlatform.DAO.EventRepository;
import com.target.VolunteeringPlatform.DAO.UserRepository;
import com.target.VolunteeringPlatform.PayloadResponse.EventParticipatedResponse;
import com.target.VolunteeringPlatform.PayloadResponse.MessageResponse;
import com.target.VolunteeringPlatform.Service.EventService;
import com.target.VolunteeringPlatform.Service.UserDetailsServiceImpl;
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
    UserDetailsServiceImpl userService;

    //Get event data by eventId
    @GetMapping(value = "/{id}")
    public Event getEventById(@PathVariable("id") int id) {
        return eventService.getById(id);
    }

    //Get all events' data (all types)
    @GetMapping(value = "/allEvents")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    //Get all past events (all types)
    @GetMapping(value = "/pastEvents")
    public List<Event> getPastEvents() {
        return eventService.getPastEvents();
    }

    //Post endpoint for users to register for an event
    @PostMapping("/registerForEvent/{eventId}/{userId}")
    public ResponseEntity<?> registerForEvent(@PathVariable("eventId") int eventId,@PathVariable("userId") int userId) {
        //check if user exists in database
        if(userService.findUserById(userId) == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("No such user exists"));
        }

        //check if user has already registered for an event
        if(eventService.checkRegisteredEvents(eventId,userId) == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("User already registered for the event"));
        }
        //add event to event list of user
        eventService.registerUserForEvent(eventId,userId);
        return ResponseEntity.ok(new MessageResponse("User Registered Successfully"));
    }

    //Get list of events according to time and event type
    @GetMapping(value = "/getEventsList/{isFutureEvent}/{eventType:[a-zA-Z &+-]*}")
    public List<Event> getEventsList(@PathVariable Boolean isFutureEvent, @PathVariable String eventType) {
        return eventService.getEvents(isFutureEvent, eventType);
    }

    //get list event registered by user
    @GetMapping(value = "/getEventParticipated/{id}")
    public EventParticipatedResponse getEventParticipated(@PathVariable("id") int userId) {
        return eventService.getEventsParticipated(userId);
    }
}
