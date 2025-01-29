package com.victor.ray.event.service.impl;

import com.victor.ray.event.beans.CreateEventRequest;
import com.victor.ray.event.beans.EventCreationResponse;
import com.victor.ray.event.beans.LocationDetail;
import com.victor.ray.event.beans.MediaDetail;
import com.victor.ray.event.entity.EventEntity;
import com.victor.ray.event.entity.UserEntity;
import com.victor.ray.event.exception.DataNotFoundException;
import com.victor.ray.event.exception.EventServiceException;
import com.victor.ray.event.repository.EventRepository;
import com.victor.ray.event.repository.UserRepository;
import com.victor.ray.event.service.EventService;
import com.victor.ray.event.utill.RestTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class EventServiceImpl implements EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RestTemplateUtil restTemplateUtil;

    private static final String URI_MEDIA = "https://searchmanagementservice-latest-8e67.onrender.com/api/v1/searchmanagement/media";
    private static final String URI_ADDRESS = "https://locationservice.onrender.com/api/v1/locationservice/address";

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository, RestTemplateUtil restTemplateUtil) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.restTemplateUtil = restTemplateUtil;
    }

    @Override
    @Transactional
    public CompletableFuture<EventCreationResponse> createEvent(CreateEventRequest createEventRequest) {
        try {
            logger.info("Creating event for userId: {}", createEventRequest.getUserId());
            UserEntity userEntity = userRepository.findByUserId(createEventRequest.getUserId())
                    .orElseThrow(() -> {
                        String errorMsg = "No user found for userId " + createEventRequest.getUserId();
                        logger.error(errorMsg);
                        return new DataNotFoundException(errorMsg);
                    });

            CompletableFuture<Long> addressSaveFuture = saveAddressAsync(createEventRequest.getLocationDetail());
            CompletableFuture<Long> mediaSaveFuture = saveMediaAsync(createEventRequest.getMediaDetail());

            return addressSaveFuture.thenCombine(mediaSaveFuture, (addressId, mediaId) -> {
                EventEntity eventEntity = createEventEntity(createEventRequest, userEntity, addressId, mediaId);
                EventEntity savedEvent = eventRepository.save(eventEntity);
                logger.info("Event created successfully with ID: {}", savedEvent.getEventId());
                return createResponse(savedEvent);
            }).exceptionally(ex -> {
                logger.error("Error occurred while creating event: {}", ex.getMessage(), ex);
                throw new EventServiceException("Failed to create event due to an internal error.", ex);
            });
        } catch (Exception e) {
            logger.error("Unhandled exception during event creation: {}", e.getMessage(), e);
            throw new EventServiceException("Unhandled exception during event creation.", e);
        }
    }

    private CompletableFuture<Long> saveAddressAsync(LocationDetail locationDetail) {
        if (locationDetail == null) {
            throw new EventServiceException("LocationDetail cannot be null.");
        }

        return CompletableFuture.supplyAsync(() -> {
            logger.info("Saving address for location: {}", locationDetail);
            return restTemplateUtil.makeRequest(URI_ADDRESS, HttpMethod.POST, locationDetail, null, Long.class);
        }).exceptionally(ex -> {
            logger.error("Error occurred while saving address detail: {}", ex.getMessage(), ex);
            throw new EventServiceException("Error occurred while saving address detail.", ex);
        });
    }

    private CompletableFuture<Long> saveMediaAsync(MediaDetail mediaDetail) {
        if (mediaDetail == null) {
            throw new EventServiceException("MediaDetail cannot be null.");
        }
        if (mediaDetail.getExternalApiId() == null) {
            throw new EventServiceException("MediaDetail externalApiId cannot be null.");
        }

        return CompletableFuture.supplyAsync(() -> {
            logger.info("Saving media detail for externalApiId: {}", mediaDetail.getExternalApiId());
            return restTemplateUtil.makeRequest(URI_MEDIA, HttpMethod.POST, mediaDetail, null, Long.class);
        }).exceptionally(ex -> {
            logger.error("Error occurred while saving media detail: {}", ex.getMessage(), ex);
            throw new EventServiceException("Error occurred while saving media detail.", ex);
        });
    }

    private EventEntity createEventEntity(CreateEventRequest request, UserEntity userEntity, Long addressId, Long mediaId) {
        LocalDateTime now = LocalDateTime.now();

        EventEntity eventEntity = new EventEntity();
        eventEntity.setTitle(request.getTitle());
        eventEntity.setDescription(request.getDescription());
        eventEntity.setStartDate(request.getStartDate());
        eventEntity.setEndDate(request.getEndDate());
        eventEntity.setActive(true);
        eventEntity.setStatus(request.getStatus());
        eventEntity.setCreatedAt(now);
        eventEntity.setUpdatedAt(now);
        eventEntity.setUserId(userEntity.getUserId());
        eventEntity.setCategoryId(request.getEventCategoryId());
        eventEntity.setSubcategoryId(request.getEventSubCatalogerId());
        eventEntity.setAddressId(addressId);
        eventEntity.setMediaId(mediaId);

        return eventEntity;
    }

    private EventCreationResponse createResponse(EventEntity savedEvent) {
        EventCreationResponse response = new EventCreationResponse();
        response.setMessage("Success");
        response.setStatus(savedEvent.getStatus());
        response.setEventId(savedEvent.getEventId());
        return response;
    }
}
