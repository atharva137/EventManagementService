package com.victor.ray.event.service;

import com.victor.ray.event.beans.CreateEventRequest;
import com.victor.ray.event.beans.EventCreationResponse;

import java.util.concurrent.CompletableFuture;

public interface EventService {

    public CompletableFuture<EventCreationResponse> createEvent(CreateEventRequest createEventRequest);
}
