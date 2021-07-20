package com.target.VolunteeringPlatform.Service;

import java.util.List;
import java.sql.Timestamp;
import java.util.Date;

import com.target.VolunteeringPlatform.DAO.EventRepository;
import com.target.VolunteeringPlatform.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    private List<Event> weekendEvents;
    private List<Event> pastEvents;

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

    public List<Event> getWeekendEvents() {
        Date date = new Date();
        Timestamp currTimestamp = new Timestamp(date.getTime());

        List<Event> allEvents = eventRepository.findAll();
        for(Event event : allEvents) {
            if (event.getStartTime().after(currTimestamp)) {
                weekendEvents.add(event);
            }
        }
        return weekendEvents;
    }

    public List<Event> getPastEvents() {
        Date date = new Date();
        Timestamp currTimestamp = new Timestamp(date.getTime());

        List<Event> allEvents = eventRepository.findAll();
        for(Event event : allEvents) {
            if (event.getStartTime().before(currTimestamp)) {
                pastEvents.add(event);
            }
        }
        return pastEvents;
    }

}