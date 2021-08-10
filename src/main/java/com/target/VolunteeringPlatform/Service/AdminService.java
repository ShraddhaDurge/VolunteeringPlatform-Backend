package com.target.VolunteeringPlatform.Service;

import com.target.VolunteeringPlatform.DAO.EventRepository;
import com.target.VolunteeringPlatform.DAO.UserRepository;
import com.target.VolunteeringPlatform.PayloadRequest.EventRequest;
import com.target.VolunteeringPlatform.model.Event;
import com.target.VolunteeringPlatform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AdminService {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    EventService eventService;

    @Autowired
    UserRepository userRepository;

    public void deleteById(int id) {
        eventRepository.deleteById(id);
    }

    public void addEvent(EventRequest event) throws IOException {
        byte[] imageBytes = event.getName().getBytes();
        Event newEvent = new Event(
                event.getEvent_type(),
                event.getName(),
                event.getDescription(),
                event.getVenue(),
                event.getStart_time(),
                event.getEnd_time(),
                imageBytes
        );
        eventRepository.save(newEvent);
        System.out.println(newEvent);
    }

    public void updateEvent(Event updateEvent) {
        Event event = eventService.getById(updateEvent.getEvent_id());
        event.setEvent_type(updateEvent.getEvent_type());
        event.setName(updateEvent.getName());
        event.setDescription(updateEvent.getDescription());
        event.setVenue(updateEvent.getVenue());
        event.setStart_time(updateEvent.getStart_time());
        event.setEnd_time(updateEvent.getEnd_time());

        eventRepository.save(event);
        System.out.println(event);
        List<User> participants = getAllParticipants(event.getName());
        for(User u : participants) {
            eventService.sendMail(u,event,"Event details updated.");
        }
    }

    public void addImageToEvent(int event_id, byte[] imageBytes) throws IOException {
        Event event = eventService.getById(event_id);
        System.out.println(event + "  "+imageBytes);
        event.setImage(imageBytes);
        System.out.println(event.getImage());
        eventRepository.save(event);
    }

    public List<User> getAllParticipants(String eventName) {
        List<User> participants = userRepository.findAll();
        List<User> eventParticipants = new ArrayList<>();

        for(User u : participants) {
            Set<Event> events = u.getEvents();
            for(Event e : events) {
                if (e.getName().equalsIgnoreCase(eventName)) {
                    eventParticipants.add(u);
                }
            }
        }
        return eventParticipants;
    }

    public void sendReminders(String eventName) {
        List<User> participants = userRepository.findAll();
        for(User u : participants) {
            Set<Event> events = u.getEvents();
            for(Event e : events) {
                if (e.getName().equalsIgnoreCase(eventName)) {
                    eventService.sendMail(u,e,"Reminder for the Event.");
                }
            }
        }
    }

}
