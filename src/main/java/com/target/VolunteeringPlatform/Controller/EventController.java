package com.target.VolunteeringPlatform.Controller;

import com.target.VolunteeringPlatform.Service.EventService;
import com.target.VolunteeringPlatform.Service.UserDetailsServiceImpl;
import com.target.VolunteeringPlatform.model.Event;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/account/events")
public class EventController {
    @Autowired
    EventService eventService;

    @Autowired
    UserDetailsServiceImpl userService;

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
        return eventService.getWeekendEvents();
    }
    @GetMapping(value = "/pastEvents")
    public List<Event> getPastEvents() {
        return eventService.getPastEvents();
    }

}
