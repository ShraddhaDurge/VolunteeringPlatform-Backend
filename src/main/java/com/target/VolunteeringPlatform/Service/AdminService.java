package com.target.VolunteeringPlatform.Service;

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
    EventService eventService;

    @Autowired
    UserService userService;

    @Autowired
    SendEmailService sendEmailService;

    public Event addEvent(EventRequest event) throws IOException {
        Event newEvent = new Event(
                event.getEvent_type(),
                event.getName(),
                event.getDescription(),
                event.getVenue(),
                event.getStart_time(),
                event.getEnd_time()
        );
        eventService.saveEvent(newEvent);
        System.out.println(newEvent);
        return newEvent;
    }

    public void updateEvent(Event updateEvent) {
        Event event = eventService.getById(updateEvent.getEvent_id());
        Event newEvent = new Event(
                event.getEvent_id(),
                updateEvent.getEvent_type(),
                updateEvent.getName(),
                updateEvent.getDescription(),
                updateEvent.getVenue(),
                updateEvent.getStart_time(),
                updateEvent.getEnd_time()
        );
        eventService.saveEvent(event);
        System.out.println(event);
        List<User> participants = getAllParticipants(event.getName());
        for(User u : participants) {
            List<Object> dateTimeDuration = eventService.getEventTimeAndDate(event.getStart_time(), event.getEnd_time());
            long min = (Long) dateTimeDuration.get(2);
            String minutes = null;
            if (min == 0)
                minutes = "";
            else
                minutes = String.valueOf(min) + " minutes";

            String emailText = "Dear " + u.getFirstname() + " " + u.getLastname() + ", \n" +
                    "Please be informed that the details of event for which you have registered for is changed." + "\n" +
                    "Updated Event Details:" + "\n\n" +
                    "   Event Name: " + event.getName() +" \n " +
                    "   Date: " + String.valueOf(dateTimeDuration.get(3)) + " \n " +
                    "   Time: "+  String.valueOf(dateTimeDuration.get(4)) + " \n " +
                    "   Duration: " + String.valueOf(dateTimeDuration.get(1)) +" hr " + minutes +" \n " +
                    "   Venue: " + event.getVenue() +"\n\n"+
                    "We sincerely apologise for any inconvenience this may cause.\n"+
                    "We are looking forward to seeing you there! \n\n" +
                    "Regards,\n" +
                    "Helping Hands Team";

            sendEmailService.sendMail(u,event,"Event details changed.", emailText);
        }
    }

    public void addImageToEvent(int event_id, byte[] imageBytes) throws IOException {
        Event event = eventService.getById(event_id);
        System.out.println(event + "  "+imageBytes);
            Event newEvent = new Event(
                    event.getEvent_id(),
                    event.getEvent_type(),
                    event.getName(),
                    event.getDescription(),
                    event.getVenue(),
                    event.getStart_time(),
                    event.getEnd_time(),
                    imageBytes
            );
        System.out.println(newEvent.getImage());
        eventService.saveEvent(newEvent);
    }

    public List<User> getAllParticipants(String eventName) {
        List<User> participants = userService.findAllUsers();
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
        List<User> participants = userService.findAllUsers();
        for(User u : participants) {
            Set<Event> events = u.getEvents();
            for(Event e : events) {
                if (e.getName().equalsIgnoreCase(eventName)) {
                    List<Object> dateTimeDuration = eventService.getEventTimeAndDate(e.getStart_time(),e.getEnd_time());

                    long min = (Long)dateTimeDuration.get(2) ;
                    String minutes = null;
                    if(min == 0 )
                        minutes = "";
                    else
                        minutes = String.valueOf(min) + " minutes";

                    String emailText = "Dear " + u.getFirstname() + " " + u.getLastname() + ", \n" +
                            e.getName() + " is coming soon and we’d love to hear from you." +"\n" +
                            "Don’t hesitate to reach out if you have questions, comments, or concerns regarding this year’s event." + "\n\n" +
                            "Event Details:" + "\n\n " +
                            "   Date: " + String.valueOf(dateTimeDuration.get(3)) + " \n " +
                            "   Time: "+  String.valueOf(dateTimeDuration.get(4)) + " \n " +
                            "   Duration: " + String.valueOf(dateTimeDuration.get(1)) +" hr " + minutes +" \n " +
                            "   Venue: " + e.getVenue() +"\n\n"+
                            "Kindly show this email at the venue for the entry.\n"+
                            "We are looking forward to seeing you there! \n\n" +
                            "Regards,\n" +
                            "Helping Hands Team";

                    sendEmailService.sendMail(u,e,"Reminder for the Event.", emailText);
                }
            }
        }
    }

}
