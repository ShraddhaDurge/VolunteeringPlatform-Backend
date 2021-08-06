package com.target.VolunteeringPlatform.model;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="event_id")
    private int event_id;

    @Column(name="event_type")
    private String event_type;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="venue")
    private String venue;

    @Column(name="start_time")
    private Timestamp start_time;

    @Column(name="end_time")
    private Timestamp end_time;

    @Column(name="image")
    @Lob
    private byte[] image;

    public Event() {
    }

    public Event(int event_id, String event_type, String name, String description, String venue, Timestamp start_time, Timestamp end_time, byte[] image) {
        this.event_id = event_id;
        this.event_type = event_type;
        this.name = name;
        this.description = description;
        this.venue = venue;
        this.start_time = start_time;
        this.end_time = end_time;
        this.image = image;
    }

    public Event(String event_type, String name, String description, String venue, Timestamp start_time, Timestamp end_time, byte[] image) {
        this.event_type = event_type;
        this.name = name;
        this.description = description;
        this.venue = venue;
        this.start_time = start_time;
        this.end_time = end_time;
        this.image = image;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] imageData) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Event{" +
                "event_id=" + event_id +
                ", event_type='" + event_type + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", venue='" + venue + '\'' +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                '}';
    }
}