package com.target.VolunteeringPlatform.PayloadRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

public class EventRequest {

    @JsonProperty("event_type")
    private String event_type;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("venue")
    private String venue;

    @JsonProperty("start_time")
    private Timestamp start_time;

    @JsonProperty("end_time")
    private Timestamp end_time;

    public EventRequest(String event_type, String name, String description, String venue, Timestamp start_time, Timestamp end_time) {
        this.event_type = event_type;
        this.name = name;
        this.description = description;
        this.venue = venue;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public EventRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    @Override
    public String toString() {
        return "EventRequest{" +
                "event_type='" + event_type + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", venue='" + venue + '\'' +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                '}';
    }
}