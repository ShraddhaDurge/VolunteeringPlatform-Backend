package com.target.VolunteeringPlatform.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import com.target.VolunteeringPlatform.DAO.EventRepository;
import com.target.VolunteeringPlatform.DAO.UserRepository;
import com.target.VolunteeringPlatform.model.Event;
import com.target.VolunteeringPlatform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    public void addEvent(Event event) {
        eventRepository.save(event);
    }

    public boolean existsByName(String name) {
        return eventRepository.existsByName(name);
    }

    public boolean existsById(int id) {
        return eventRepository.existsById(id);
    }

    public Event getById(int event_id) {
        return eventRepository.findById(event_id).orElseThrow(() -> new RuntimeException("Cannot Get Event By Id"));
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void deleteById(int id) {
        eventRepository.deleteById(id);
    }

    public List<Event> getWeekendEvents(int futureEvent) {
        Date date = new Date();
        Timestamp currTimestamp = new Timestamp(date.getTime());
        List<Event> futureEvents = new ArrayList<>();
        List<Event> pastEvents = new ArrayList<>();
        List<Event> allEvents = eventRepository.findAll();
        for(Event event : allEvents) {
            if (event.getEvent_type().equalsIgnoreCase("Weekend event")) {
                if(event.getStart_time().after(currTimestamp)) {
                    futureEvents.add(event);
                    System.out.println(event.getName() + " added");
                } else {
                    pastEvents.add(event);
                }
            }
        }
        if(futureEvent == 1)
            return futureEvents;
        else
            return pastEvents;
    }

    public List<Event> getPastEvents() {
        Date date = new Date();
        Timestamp currTimestamp = new Timestamp(date.getTime());

        List<Event> pastEvents = new ArrayList<>();
        List<Event> allEvents = eventRepository.findAll();

        for(Event event : allEvents) {
            if (event.getStart_time().before(currTimestamp)) {
                pastEvents.add(event);
            }
        }
        return pastEvents;
    }

    public void registerUserForEvent(int eventId, int userId) {
        User user = userRepository.findById(userId);
        Event event = eventRepository.getById(eventId);
        Set<Event> events = user.getEvents();
        events.add(event);
        user.setEvents(events);
        userRepository.save(user);
        //sendMail
    }

    public List<User> getAllParticipantsByEventId(int eventId) {
        Event event = eventRepository.getById(eventId);
        List<User> participants = userRepository.findAll();
        List<User> eventParticipants = new ArrayList<>();
        for(User u : participants) {
            if(u.getEvents().contains(event)) {
                eventParticipants.add(u);
            }
        }
        return eventParticipants;
    }

    public List<Event> getNGOWebinarsEvents(int futureEvent) {
        Date date = new Date();
        Timestamp currTimestamp = new Timestamp(date.getTime());
        List<Event> futureEvents = new ArrayList<>();
        List<Event> pastEvents = new ArrayList<>();
        List<Event> allEvents = eventRepository.findAll();
        for(Event event : allEvents) {
            if (event.getEvent_type().equalsIgnoreCase("NGO Webinars")) {
                if(event.getStart_time().after(currTimestamp)) {
                    futureEvents.add(event);
                    System.out.println(event.getName() + " added");
                } else {
                    pastEvents.add(event);
                }
            }
        }
        if(futureEvent == 1)
            return futureEvents;
        else
            return pastEvents;
    }

    public List<Event> getFoodForThoughtEvents(int futureEvent) {
        Date date = new Date();
        Timestamp currTimestamp = new Timestamp(date.getTime());
        List<Event> futureEvents = new ArrayList<>();
        List<Event> pastEvents = new ArrayList<>();
        List<Event> allEvents = eventRepository.findAll();
        for(Event event : allEvents) {
            if (event.getEvent_type().equalsIgnoreCase("Food For Thought")) {
                if(event.getStart_time().after(currTimestamp)) {
                    futureEvents.add(event);
                    System.out.println(event.getName() + " added");
                } else {
                    pastEvents.add(event);
                }
            }
        }
        if(futureEvent == 1)
            return futureEvents;
        else
            return pastEvents;
    }

    public List<Event> getArtAndCraftsEvents(int futureEvent) {
        Date date = new Date();
        Timestamp currTimestamp = new Timestamp(date.getTime());
        List<Event> futureEvents = new ArrayList<>();
        List<Event> pastEvents = new ArrayList<>();
        List<Event> allEvents = eventRepository.findAll();
        for(Event event : allEvents) {
            if (event.getEvent_type().equalsIgnoreCase("Art And Craft")) {
                if(event.getStart_time().after(currTimestamp)) {
                    futureEvents.add(event);
                    System.out.println(event.getName() + " added");
                } else {
                    pastEvents.add(event);
                }
            }
        }
        if(futureEvent == 1)
            return futureEvents;
        else
            return pastEvents;
    }
}