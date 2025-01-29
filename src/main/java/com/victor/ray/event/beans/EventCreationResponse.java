package com.victor.ray.event.beans;


public class EventCreationResponse {

    private String status;  // 'success' or 'failure'
    private String message; // Success or failure message
    private Long eventId;   // ID of the created event


    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }



}
