package com.target.VolunteeringPlatform.Controller;

import com.target.VolunteeringPlatform.PayloadRequest.EventRequest;
import com.target.VolunteeringPlatform.PayloadResponse.MessageResponse;
import com.target.VolunteeringPlatform.Service.AdminService;
import com.target.VolunteeringPlatform.Service.EventService;
import com.target.VolunteeringPlatform.model.Event;
import com.target.VolunteeringPlatform.model.User;

import javax.validation.Valid;
//import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

//Controller class for Admin specific functionalities
@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/account/admin")
public class AdminController {
    @Autowired
    EventService eventService;

    @Autowired
    AdminService adminService;

    //Post endpoint to add new event details in database
    @PostMapping(value = "/addEvents")
    public ResponseEntity<?> addEvent(@Valid @RequestBody EventRequest eventRequest){
        //check if event name already exist in database
        if (eventService.existsByName(eventRequest.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Event name already exists!"));
        }
        System.out.println(eventRequest);

        try {
            //add event details in database
            Event eventResponse = adminService.addEvent(eventRequest);
            return ResponseEntity.ok(eventResponse);
        } catch (IOException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error:Event cannot be added"));
        }
    }

    //Post endpoint to add image of an event in database
    @PostMapping(value = "/addImage/{event_id}", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addImageToEvent(@PathVariable(value = "event_id") int event_id, @RequestParam(value = "file") MultipartFile image){

        if (!eventService.existsById(event_id)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Event do not exist!"));
        }
        System.out.println(event_id+"  "+image);

        try {
            //add images of an event in database
            byte[] imageBytes = image.getBytes();
            adminService.addImageToEvent(event_id,imageBytes);
            return ResponseEntity.ok(new MessageResponse("Event Image Added Successfully!"));
        } catch (IOException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Cannot upload image!"));
        }
    }

    //Post endpoint to delete event details from database
    @DeleteMapping(value = "/events/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") int id) {
        //check if event exists in database
        if (!eventService.existsById(id)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Event Id doesn't exist!"));
        }
        eventService.deleteById(id);        //delete event details from database
        return ResponseEntity.ok(new MessageResponse("Event Deleted Successfully!"));
    }

    //Post endpoint to update event details in database
    @PostMapping(value = "/updateEvents")
    public ResponseEntity<?> updateEvent(@Valid @RequestBody Event updateEvent) {
        //check if event exists in database
        if (!eventService.existsById(updateEvent.getEvent_id())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Event Id doesn't exist!"));
        }
        adminService.updateEvent(updateEvent);      //update event details in database
        return ResponseEntity.ok(new MessageResponse("Event Updated Successfully!"));
    }

    //Get endpoint to get list of all participants of an event from database
    @GetMapping(value = "/getAllParticipants/{event_name:[a-zA-Z &+-]*}")
    public List<User> getAllParticipants(@PathVariable("event_name") String event_name) {
        return adminService.getAllParticipants(event_name);
    }

    //Send reminder emails to all participants of an event
    @PostMapping(value = "/sendReminders/{event_name:[a-zA-Z &+-]*}")
    public ResponseEntity<?> sendReminders(@PathVariable("event_name") String event_name) {
        //check if event exists in database
        if (!eventService.existsByName(event_name)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Event Id doesn't exist"));
        }
        adminService.sendReminders(event_name);         //send reminder mails
        return ResponseEntity.ok(new MessageResponse("Emails are sent successfully!"));
    }
}
