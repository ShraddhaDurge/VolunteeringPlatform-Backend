package com.target.VolunteeringPlatform.PayloadResponse;


import com.target.VolunteeringPlatform.model.Event;

import java.util.Set;

public class EventParticipatedResponse {

    private Set<Event> events;

    private int eventsCount;
    public EventParticipatedResponse(Set<Event> events, int eventsCount) {
        this.events = events;
        this.eventsCount = eventsCount;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public int getEventsCount() {
        return eventsCount;
    }

    public void setEventsCount(int eventsCount) {
        this.eventsCount = eventsCount;
    }

    @Override
    public String toString() {
        return "EventParticipatedResponse{" +
                "events=" + events +
                ", eventsCount=" + eventsCount +
                '}';
    }
}
