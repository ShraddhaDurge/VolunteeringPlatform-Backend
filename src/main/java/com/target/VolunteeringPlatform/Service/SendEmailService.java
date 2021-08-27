package com.target.VolunteeringPlatform.Service;

import com.lowagie.text.DocumentException;
import com.target.VolunteeringPlatform.model.Event;
import com.target.VolunteeringPlatform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class SendEmailService {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendMail(User user, Event event, String emailSubject, String emailText) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getEmail());
        msg.setFrom(event.getName()+ " Team <helpinghands.igniteplus@gmail.com>");
        msg.setSubject(emailSubject);
        msg.setText(emailText);
        try{
            javaMailSender.send(msg);
            System.out.println("Email sent successfully!");
        } catch(Exception e) {
            System.out.println("Email not sent.");
            e.printStackTrace();
        }
    }

    public void sendEmailWithAttachment(User user, Event event, String emailSubject, String emailText, String pathToAttachment) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(user.getEmail());
            helper.setFrom(event.getName()+ " Team <helpinghands.igniteplus@gmail.com>");
            helper.setSubject(emailSubject);
            helper.setText(emailText);

            //FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            File file = new File(pathToAttachment);
            helper.addAttachment("Certificate", file);

            javaMailSender.send(message);

        } catch (MessagingException e) {
            System.out.println("Certificate not sent.");
            e.printStackTrace();
        }
    }

    void sendHtmlMessage(User user, Event event, String emailSubject, String htmlBody) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//        FileSystemResource file = new FileSystemResource(attachment);
//        helper.addInline(attachment.getName(), file);
        helper.setFrom(event.getName()+ " Team <helpinghands.igniteplus@gmail.com>");
        helper.setTo(user.getEmail());
        helper.setSubject(emailSubject);
        helper.setText(htmlBody, true);
        javaMailSender.send(message);
    }

    public String parseThymeleafTemplate(String firstName, String lastName, String eventName, String venue, String eventDate, String template) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("Firstname", firstName);
        context.setVariable("Surname", lastName);
        context.setVariable("eventName", eventName);
        context.setVariable("eventVenue", venue);
        context.setVariable("eventDate", eventDate);

        System.out.println(firstName + "  " + lastName + "  " +  eventName + "  " + venue + "  " + eventDate);

        return templateEngine.process(template, context);
    }

    public String parseReminderTemplate(String firstName, String lastName, String eventName, String venue, String eventDate, String eventTime, String eventDuration, String template) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("Firstname", firstName);
        context.setVariable("Surname", lastName);
        context.setVariable("eventName", eventName);
        context.setVariable("eventVenue", venue);
        context.setVariable("eventDate", eventDate);
        context.setVariable("eventDuration", eventName);
        context.setVariable("eventTime", venue);
        System.out.println(firstName + "  " + lastName + "  " +  eventName + "  " + venue + "  " + eventDate);

        return templateEngine.process(template, context);
    }

    public void generatePdfFromHtml(String html, int userId) throws IOException, DocumentException {
        String fileName = userId + ".pdf";
        String outputFolder = System.getProperty("user.home") + File.separator + fileName;
        OutputStream outputStream = new FileOutputStream(outputFolder);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        System.out.println("PDF GENERATED");
        outputStream.close();
    }

}
