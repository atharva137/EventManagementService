package com.victor.ray.event.beans;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class CreateEventRequest {

    @NotNull(message = "userId cannot be null")
    private Long userId;
    private String creatorName;

    @NotNull(message = "title cannot be null")
    @NotEmpty(message = "title cannot be empty")
    private String title;

    private String description;

    private Long eventCategoryId;

    private Long eventSubCatalogerId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String status;

    private LocationDetail locationDetail;

    private MediaDetail mediaDetail;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEventCategoryId() {
        return eventCategoryId;
    }

    public void setEventCategoryId(Long eventCategoryId) {
        this.eventCategoryId = eventCategoryId;
    }

    public Long getEventSubCatalogerId() {
        return eventSubCatalogerId;
    }

    public void setEventSubCatalogerId(Long eventSubCatalogerId) {
        this.eventSubCatalogerId = eventSubCatalogerId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocationDetail getLocationDetail() {
        return locationDetail;
    }

    public void setLocationDetail(LocationDetail locationDetail) {
        this.locationDetail = locationDetail;
    }

    public MediaDetail getMediaDetail() {
        return mediaDetail;
    }

    public void setMediaDetail(MediaDetail mediaDetail) {
        this.mediaDetail = mediaDetail;
    }
}

