package com.target.VolunteeringPlatform.Controller;

import com.target.VolunteeringPlatform.PayloadRequest.EventRequest;
import com.target.VolunteeringPlatform.PayloadResponse.MessageResponse;
import com.target.VolunteeringPlatform.Service.EventService;
import com.target.VolunteeringPlatform.Service.UserDetailsServiceImpl;
import com.target.VolunteeringPlatform.model.Event;
import com.target.VolunteeringPlatform.model.User;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
//import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/account/admin")
public class AdminController {
    @Autowired
    EventService eventService;

    @PostMapping(value = "/addEvents")
    public ResponseEntity<?> addEvent(@Valid @RequestBody EventRequest eventRequest){
        if (eventService.existsByName(eventRequest.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Event name already exists!"));
        }
        System.out.println(eventRequest);

        try {
            eventService.addEvent(eventRequest);
            return ResponseEntity.ok(new MessageResponse("Event Added Successfully!"));
        } catch (IOException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error:Event cannot be added"));
        }
    }

    @PostMapping(value = "/addImage/{event_id}", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addImageToEvent(@PathVariable(value = "event_id") int event_id, @RequestParam(value = "file") MultipartFile image){
        if (!eventService.existsById(event_id)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Event do not exist!"));
        }
        System.out.println(event_id+"  "+image);

        try {
            byte[] imageBytes = image.getBytes();
            eventService.addImageToEvent(event_id,imageBytes);
            return ResponseEntity.ok(new MessageResponse("Event Image Added Successfully!"));
        } catch (IOException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Cannot upload image!"));
        }
    }

    @DeleteMapping(value = "/events/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") int id) {
        if (!eventService.existsById(id)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Event Id doesn't exist!"));
        }
        eventService.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Event Deleted Successfully!"));
    }

//    @PostMapping(value = "/updateEvents")
//    public ResponseEntity<?> updateEvent(@Valid @RequestBody EventRequest eventRequest) {
//        try {
//            eventService.addEvent(eventRequest);
//            return ResponseEntity.ok(new MessageResponse("Event Added Successfully!"));
//
//        } catch (IOException e) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error:Event not added. Cannot upload image!"));
//        }
//    }

    @GetMapping(value = "/getAllParticipants/{event_name:[a-zA-Z &+-]*}")
    public List<User> getAllParticipants(@PathVariable("event_name") String event_name) {
        return eventService.getAllParticipants(event_name);
    }

    @PostMapping(value = "/sendReminders/{event_name:[a-zA-Z &+-]*}")
    public ResponseEntity<?> sendReminders(@PathVariable("event_name") String event_name) {
        if (!eventService.existsByName(event_name)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Event Id doesn't exist"));
        }
        eventService.sendReminders(event_name);
        return ResponseEntity.ok(new MessageResponse("Emails are sent successfully!"));
    }
}
