package com.target.VolunteeringPlatform.Service;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import com.target.VolunteeringPlatform.DAO.EventRepository;
import com.target.VolunteeringPlatform.DAO.UserRepository;
import com.target.VolunteeringPlatform.RequestResponse.EventParticipatedResponse;
import com.target.VolunteeringPlatform.model.Event;
import com.target.VolunteeringPlatform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailSender javaMailSender;

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
        sendMail(user,event);
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

    public List<Event> getEvents(Boolean isFutureEvent, String eventType) {
        Date date = new Date();
        Timestamp currTimestamp = new Timestamp(date.getTime());
        List<Event> futureEvents = new ArrayList<>();
        List<Event> pastEvents = new ArrayList<>();
        List<Event> allEvents = eventRepository.findAll();
        for(Event event : allEvents) {
            if (event.getEvent_type().equalsIgnoreCase(eventType)) {
                if(event.getStart_time().after(currTimestamp)) {
                    futureEvents.add(event);
                } else {
                    pastEvents.add(event);
                }
            }
        }
        if(isFutureEvent == true)
            return futureEvents;
        else
            return pastEvents;
    }

//    public int getEventParticipatedCount(int userId) {
//        User user = userRepository.findById(userId);
//        Set<Event> events = user.getEvents();
//        int eventCount = 0;
//        for(Event event : events) {
//           eventCount++;
//        }
//        return eventCount;
//    }

    public EventParticipatedResponse getEventsParticipated(int userId) {

        User user = userRepository.findById(userId);
        Set<Event> events = user.getEvents();
        int eventCount = 0;
        for(Event event : events) {
            eventCount++;
        }
        EventParticipatedResponse eventResponse = new EventParticipatedResponse(events,eventCount);
        return eventResponse;
    }

    public void sendMail(User user, Event event) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getEmail());
        msg.setFrom(event.getName()+ " Team <helping.hands@gmail.com>");
        msg.setSubject("Successfully Registered");
        msg.setText(
                "Dear " + user.getFirstname() + " " + user.getLastname() + ", \n" +
                        "You have been successfully registered for " + event.getName() + ". \n" +
                        "This volunteering event is for "+ event.getDescription() + ".\n" +
                        "The duration of event is from "+ event.getStart_time() + " to " + event.getEnd_time()+". \n" +
                        "Kindly show this email at the venue for the entry.\n\n" +
                        "Regards,\n" +
                        "Helping Hands Team"
        );
        try{
            javaMailSender.send(msg);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}