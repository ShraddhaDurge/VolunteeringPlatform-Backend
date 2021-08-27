package com.target.VolunteeringPlatform.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.target.VolunteeringPlatform.DAO.EventRepository;
import com.target.VolunteeringPlatform.PayloadResponse.EventParticipatedResponse;
import com.target.VolunteeringPlatform.model.Event;
import com.target.VolunteeringPlatform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserService userService;

    @Autowired
    SendEmailService sendEmailService;

    public boolean existsByName(String name) {
        return eventRepository.existsByName(name);
    }

    public boolean existsById(int id) {
        return eventRepository.existsById(id);
    }

    public void deleteById(int id) {
        eventRepository.deleteById(id);
    }

    public Event getById(int event_id) {
        return eventRepository.findById(event_id).orElseThrow(() -> new RuntimeException("Cannot Get Event By Id"));
    }

    public Event getByName(String eventName) {
        return eventRepository.findByName(eventName);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void saveEvent(Event event) {eventRepository.save(event);}

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
        User user = userService.findUserById(userId);
        Event event = eventRepository.getById(eventId);
        Set<Event> events = user.getEvents();
        events.add(event);
        user.setEvents(events);

        List<Object> dateTimeDuration = getEventTimeAndDate(event.getStart_time(), event.getEnd_time());

        long min = (Long)dateTimeDuration.get(2) ;
        String minutes;
        if(min == 0 )
            minutes = " ";
        else
            minutes = min + " minutes";

        String emailText = "Dear " + user.getFirstname() + " " + user.getLastname() + ", \n" +
                "You have been successfully registered for " + event.getName() + "!" + "\n" +
                "Thank you for signing up for event designed for following purpose.\n " + event.getDescription() + ".\n\n" +
                "Event Details:" + "\n\n " +
                "   Date: " + String.valueOf(dateTimeDuration.get(3)) + " \n " +
                "   Time: "+  String.valueOf(dateTimeDuration.get(4)) + " \n " +
                "   Duration: " + String.valueOf(dateTimeDuration.get(1)) +" hr " + minutes +" \n " +
                "   Venue: " + event.getVenue() +"\n\n"+
                "Kindly show this email at the venue for the entry.\n\n"+
                "We are looking forward to seeing you there! \n\n" +
                "Regards,\n" +
                "Helping Hands Team";

        if(user.getHours() == null )
            user.setHours((Double) dateTimeDuration.get(0));
        else
            user.setHours(user.getHours() +(Double) dateTimeDuration.get(0));
        System.out.println(user.getHours());
        userService.saveUser(user);

        sendEmailService.sendMail(user,event,"Successfully Registered",emailText);

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

        User user = userService.findUserById(userId);
        Set<Event> events = user.getEvents();
        int eventCount = 0;
        for (Event event : events) {
            eventCount++;
        }
        EventParticipatedResponse eventResponse = new EventParticipatedResponse(events, eventCount);
        return eventResponse;
    }

    public Boolean checkRegisteredEvents(int eventId,int userId) {
        Event event = getById(eventId);
        User user = userService.findUserById(userId);
        Set<Event> events = user.getEvents();

        //check if user has already registers for an event
        if(events.contains(event)) {
            return true;
        }
        return false;
    }


    public List<Object> getEventTimeAndDate(Timestamp startTime, Timestamp endTime)
    {
        List<Object> dateTimeDuration = new ArrayList<Object>();
        long timeDiff = endTime.getTime() - startTime.getTime();
        double seconds = (double) (timeDiff / 1000);
        double hours = (seconds / 3600);

        dateTimeDuration.add(hours);

        long daysLong = TimeUnit.MILLISECONDS.toDays(timeDiff);
        long remainingHoursInMillis = timeDiff - TimeUnit.DAYS.toMillis(daysLong);
        long hoursLong = TimeUnit.MILLISECONDS.toHours(remainingHoursInMillis);
        long remainingMinutesInMillis = remainingHoursInMillis - TimeUnit.HOURS.toMillis(hoursLong);
        long minutesLong = TimeUnit.MILLISECONDS.toMinutes(remainingMinutesInMillis);

        dateTimeDuration.add(hoursLong);
        dateTimeDuration.add(minutesLong);

        System.out.println(hours + " , " + hoursLong + " , " + minutesLong);

        List<String> formattedTimestamp = formatDateTime(startTime.toString());
        dateTimeDuration.add(formattedTimestamp.get(0));     //date
        dateTimeDuration.add(formattedTimestamp.get(1));    //time

        return dateTimeDuration;
    }

    public List<String> formatDateTime(String timestamp)
    {
        List<String> formattedDateTime = new ArrayList<>();
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
        Date d = null;
        try {
            d = f.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat time = new SimpleDateFormat("hh:mm:ss.S");
        System.out.println("Date: " + date.format(d));
        System.out.println("Time: " + time.format(d));
        formattedDateTime.add(date.format(d));
        formattedDateTime.add(time.format(d));

        return formattedDateTime;
    }
}