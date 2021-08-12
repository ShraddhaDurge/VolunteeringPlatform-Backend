package com.target.VolunteeringPlatform.Service;

import com.target.VolunteeringPlatform.DAO.EventRepository;
import com.target.VolunteeringPlatform.DAO.UserRepository;
import com.target.VolunteeringPlatform.PayloadRequest.EventRequest;
import com.target.VolunteeringPlatform.model.Event;
import com.target.VolunteeringPlatform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
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
                    "Event Name: " + event.getName() +" \n " +
                    "   Date: " + String.valueOf(dateTimeDuration.get(3)) + " \n " +
                    "   Time: "+  String.valueOf(dateTimeDuration.get(4)) + " \n " +
                    "   Duration: " + String.valueOf(dateTimeDuration.get(1)) +" hr " + minutes +" \n " +
                    "   Venue: " + event.getVenue() +"\n\n"+
                    "We sincerely apologise for any inconvenience this may cause.\n"+
                    "We are looking forward to seeing you there! \n\n" +
                    "Regards,\n" +
                    "Helping Hands Team";

            eventService.sendMail(u,event,"Event details changed.", emailText);
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

                    eventService.sendMail(u,e,"Reminder for the Event.", emailText);
                }
            }
        }
    }

}
