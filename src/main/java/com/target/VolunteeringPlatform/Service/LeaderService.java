package com.target.VolunteeringPlatform.Service;

import com.target.VolunteeringPlatform.model.Event;
import com.target.VolunteeringPlatform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import com.lowagie.text.DocumentException;

import javax.mail.MessagingException;

@Service
public class LeaderService {

    @Autowired
    UserService userService;

    @Autowired
    EventService eventService;

    @Autowired
    SendEmailService sendEmailService;

    @Autowired
    AdminService adminService;

    public Map<User, Double> getTimeSpentByUser() {
        List<User> users = userService.findAllUsers();
        Map<User, Double> userTimeSpent = new HashMap<>();
        for(User u : users) {
            System.out.println(u.getHours());
            userTimeSpent.put(u,u.getHours());
        }
        return userTimeSpent;
    }

    public void sendCertificates(int eventId)
    {
        Event event = eventService.getById(eventId);
        System.out.println(event);
        List<User> participants = adminService.getAllParticipants(event.getName());
        //List<Object> dateTimeDuration = eventService.getEventTimeAndDate(event.getStart_time(), event.getEnd_time());
        Date date = new Date();
        Timestamp currTimestamp = new Timestamp(date.getTime());

        String dateIssued = eventService.formatDateTime(currTimestamp.toString()).get(0);   //current date
        System.out.println("Date: " + dateIssued);

        for(User u : participants) {
            System.out.println(u);
            String html = sendEmailService.parseThymeleafTemplate(u.getFirstname(), u.getLastname(), event.getName(),
                    event.getVenue(), dateIssued, "certificate_template");
            try {
                System.out.println("PDF Generating..");
                sendEmailService.generatePdfFromHtml(html, u.getId());
                String pathToAttachment = System.getProperty("user.home") + File.separator + u.getId() + ".pdf";
                System.out.println(pathToAttachment);
                sendEmailService.sendEmailWithAttachment(u,event,"Certificate of Participation",
                        "Thank you!",pathToAttachment);
                System.out.println("Certificate sent");
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Integer> userAnalyticsCounts() {
        List<User> users = userService.findAllUsers();
        List<Event> allEvents = eventService.getEvents(true,"Weekend event");
        List<Event> pastEvents = eventService.getEvents(false,"Weekend event");
        List<Integer> userAnalytics = new ArrayList<>();
        int eventHours = 0;
        allEvents.addAll(pastEvents);

        for(Event e : allEvents) {
            List<Object> dateTimeDuration = eventService.getEventTimeAndDate(e.getStart_time(),e.getEnd_time());
            eventHours += ((Long) dateTimeDuration.get(1)).intValue();
        }
        userAnalytics.add(Math.toIntExact(eventHours));  //Total No. of hours of events
        userAnalytics.add(users.size());                //No. of users(all roles)
        userAnalytics.add(allEvents.size());         //No. of events(future + past)
        return userAnalytics;

    }

    public void sendNominationCard(int userId, String eventName)
    {
        System.out.println(eventName);
        Event event = eventService.getByName(eventName);
        System.out.println(event);
        Date date = new Date();
        Timestamp currTimestamp = new Timestamp(date.getTime());

        String dateIssued = eventService.formatDateTime(currTimestamp.toString()).get(0);   //current date
        System.out.println("Date: " + dateIssued);

        User u = userService.findUserById(userId);
        System.out.println(u);
        String html = sendEmailService.parseThymeleafTemplate(u.getFirstname(), u.getLastname(), event.getName(),
                    event.getVenue(), dateIssued, "nomination_card");

        try {
            sendEmailService.sendHtmlMessage(u,event,"Rewards and Recognition", html);
            System.out.println("Nomination card sent");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        System.out.println("Nomination card send");
    }

}
