package com.target.VolunteeringPlatform.Service;

import com.target.VolunteeringPlatform.DAO.EventRepository;
import com.target.VolunteeringPlatform.DAO.UserRepository;
import com.target.VolunteeringPlatform.model.Event;
import com.target.VolunteeringPlatform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import com.lowagie.text.DocumentException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

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
                String pathToAttachment = System.getProperty("user.home") + File.separator + String.valueOf(u.getId()) + ".pdf";
                System.out.println(pathToAttachment);
                sendEmailService.sendEmailWithAttachment(u,event,"Certificate of Participation",
                        "Thank you!",pathToAttachment);
                System.out.println("Certificate send");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    }


}
