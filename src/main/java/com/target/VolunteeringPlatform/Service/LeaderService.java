package com.target.VolunteeringPlatform.Service;

import com.target.VolunteeringPlatform.model.Event;
import com.target.VolunteeringPlatform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import com.lowagie.text.DocumentException;

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
        List<Object> dateTimeDuration = eventService.getEventTimeAndDate(event.getStart_time(), event.getEnd_time());
        for(User u : participants) {
            System.out.println(u);
            String html = sendEmailService.parseThymeleafTemplate(u.getFirstname(), u.getLastname(), event.getName(), event.getVenue(),String.valueOf(dateTimeDuration.get(3)));
            try {
                System.out.println("PDF Generating..");
                sendEmailService.generatePdfFromHtml(html, u.getId());
                String pathToAttachment = System.getProperty("user.home") + File.separator + u.getId() + ".pdf";
                System.out.println(pathToAttachment);
                sendEmailService.sendEmailWithAttachment(u,event,"Certificate of Participation",
                        "Thank you!",pathToAttachment);
                System.out.println("Certificate send");
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, Integer> userAnalyticsCounts() {
        List<User> users = userService.findAllUsers();
        List<Event> events = eventService.getAllEvents();
        List<User> staffs = new ArrayList<>();
        Map<String, Integer> userAnalytics = new HashMap<>();
        userAnalytics.put("Events Count",events.size());
        userAnalytics.put("Users Count",users.size());

        for(User u : users) {
            if(u.getRole().equalsIgnoreCase("LEADER") || u.getRole().equalsIgnoreCase("ADMIN"))
                staffs.add(u);
        }
        userAnalytics.put("Management members",staffs.size());

        return userAnalytics;

    }


}
