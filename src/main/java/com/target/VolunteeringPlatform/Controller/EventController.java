package com.target.VolunteeringPlatform.Controller;

import com.target.VolunteeringPlatform.DAO.EventRepository;
import com.target.VolunteeringPlatform.DAO.UserRepository;
import com.target.VolunteeringPlatform.RequestResponse.MessageResponse;
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
    public List<Event> getEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping(value = "/weekendEvents")
    public List<Event> getWeekendEvents() {
        int futureEvent = 1;
        return eventService.getWeekendEvents(futureEvent);
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

    @GetMapping(value = "/getAllParticipants/{id}")
    public List<User> getAllParticipants(@PathVariable("id") int eventId) {
        return eventService.getAllParticipantsByEventId(eventId);
    }

    @GetMapping(value = "/ngoWebinars")
    public List<Event> getNGOWebinarEvents() {
        int futureEvent = 1;
        return eventService.getNGOWebinarsEvents(futureEvent);
    }

    @GetMapping(value = "/foodForThoughtEvents")
    public List<Event> getFoodForThoughtEvents() {
        int futureEvent = 1;
        return eventService.getFoodForThoughtEvents(futureEvent);
    }

    @GetMapping(value = "/artAndCraftEvents")
    public List<Event> getArtAndCraftEvents() {
        int futureEvent = 1;
        return eventService.getArtAndCraftsEvents(futureEvent);
    }

    @GetMapping(value = "/pastWeekendEvents")
    public List<Event> getPastWeekendEvents() {
        int pastEvent = 0;
        return eventService.getWeekendEvents(pastEvent);
    }

    @GetMapping(value = "/pastNgoWebinars")
    public List<Event> getPastNGOWebinarEvents() {
        int pastEvent = 0;
        return eventService.getNGOWebinarsEvents(pastEvent);
    }

    @GetMapping(value = "/pastFoodForThoughtEvents")
    public List<Event> getPastFoodForThoughtEvents() {
        int pastEvent = 0;
        return eventService.getFoodForThoughtEvents(pastEvent);
    }

    @GetMapping(value = "/pastArtAndCraftEvents")
    public List<Event> getPastArtAndCraftEvents() {
        int pastEvent = 0;
        return eventService.getArtAndCraftsEvents(pastEvent);
    }

}
