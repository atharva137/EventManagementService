package com.victor.ray.event.validations;

import com.victor.ray.event.beans.CreateEventRequest;
import com.victor.ray.event.exception.InvalidInputException;
import com.victor.ray.event.helper.EventServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class EventCreationRequestValidation {



    private final EventServiceHelper eventServiceHelper;

    @Autowired
    public EventCreationRequestValidation(EventServiceHelper eventServiceHelper){
        this.eventServiceHelper = eventServiceHelper;
    }

    public void validateCreateEventRequest(CreateEventRequest createEventRequest) {
        if (Objects.isNull(createEventRequest)) {
            throw new InvalidInputException("Create event request cannot be empty");
        }
        validateUserId(createEventRequest.getUserId());
        validateDates(createEventRequest.getStartDate(), createEventRequest.getEndDate());
    }

    private void validateUserId(Long userId) {
        if (Objects.isNull(userId)) {
            throw new InvalidInputException("userId cannot be empty");
        }
    }

    private void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (Objects.isNull(startDate)) {
            throw new InvalidInputException("Start date cannot be null");
        }
        if (Objects.isNull(endDate)) {
            throw new InvalidInputException("End date cannot be null");
        }
        if (!eventServiceHelper.isValidDateTime(startDate.toString())) {
            throw new InvalidInputException("Invalid date format for start date: " + startDate);
        }
        if (!eventServiceHelper.isValidDateTime(endDate.toString())) {
            throw new InvalidInputException("Invalid date format for end date: " + endDate);
        }
        // Additional business logic can be added here (e.g., start date should be before end date)
    }
}
