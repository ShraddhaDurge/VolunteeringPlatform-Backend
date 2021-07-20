package com.target.VolunteeringPlatform.model;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="event_id")
    private int event_id;

    @Column(name="eventType")
    private String eventType;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="venue")
    private String venue;

    @Column(name="startTime")
    private Timestamp startTime;

    @Column(name="endTime")
    private Timestamp endTime;

    public Event(String eventType, String name, String description, String venue, Timestamp startTime,Timestamp endTime) {
        this.eventType = eventType;
        this.name = name;
        this.description = description;
        this.venue = venue;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Event(int event_id, String eventType, String name, String description, String venue, Timestamp startTime,Timestamp endTime) {
        this.event_id = event_id;
        this.eventType = eventType;
        this.name = name;
        this.description = description;
        this.venue = venue;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Event() {
    }


    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
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

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}