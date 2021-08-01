package com.target.VolunteeringPlatform.Service;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import com.target.VolunteeringPlatform.DAO.EventRepository;
import com.target.VolunteeringPlatform.DAO.UserRepository;
import com.target.VolunteeringPlatform.PayloadResponse.EventParticipatedResponse;
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
        Event addEvent = new Event(
                event.getEvent_type(),
                event.getName(),
                event.getDescription(),
                event.getVenue(),
                event.getStart_time(),
                event.getEnd_time(),
                event.getImage()
        );

        eventRepository.save(event);
    }

    public boolean existsByName(String name) {
        return eventRepository.existsByName(name);
    }

    public boolean existsById(int id) {
        return eventRepository.existsById(id);
    }

    public Event findByName(String name) {
        return eventRepository.findByName(name);
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
        sendMail(user,event,"Successfully Registered");
    }

    public List<User> getAllParticipants(String eventName) {
        //Event event = eventRepository.findByName(eventName);
        List<User> participants = userRepository.findAll();
        List<User> eventParticipants = new ArrayList<>();
//        for(User u : participants) {
//            if(u.getEvents().contains(event)) {
//                eventParticipants.add(u);
//            }
//        }
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

    public void sendMail(User user, Event event, String emailSubject) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getEmail());
        msg.setFrom(event.getName()+ " Team <helpinghands.igniteplus@gmail.com>");
        msg.setSubject(emailSubject);
        msg.setText(
                "Dear " + user.getFirstname() + " " + user.getLastname() + ", \n" +
                        "   You have been successfully registered for " + event.getName() + "." +
                        "Thank you for signing up for "+ event.getDescription() + ".\n\n"+
                        "Start Time: "+ event.getStart_time() + " \n " +
                        "End Time: "+event.getEnd_time()+". \n\n" +
                        "Kindly show this email at the venue for the entry.\n"+
                        "We are looking forward to seeing you there! \n\n" +
                        "Regards,\n" +
                        "Helping Hands Team"
        );
        try{
            javaMailSender.send(msg);
            System.out.println("Email sent successfully!");
        } catch(Exception e) {
            System.out.println("Email not sent.");
            e.printStackTrace();
        }
    }

    public void sendReminders(String eventName) {
        //Event event = eventRepository.findByName(eventName);
        List<User> participants = userRepository.findAll();
        for(User u : participants) {
            Set<Event> events = u.getEvents();
            for(Event e : events) {
                if (e.getName().equalsIgnoreCase(eventName)) {
                    sendMail(u,e,"Reminder for the Event.");
                }
            }
        }
//        for(User u : participants) {
//            if(u.getEvents().contains(event)) {
//                sendMail(u,event,"Reminder for the Event.");
//            }
//        }
    }
}