package com.backend.api.services;

import com.backend.api.dtos.EventDto;
import com.backend.api.models.EventModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Iterator;
import java.util.List;

public interface EventService {
    Flux<EventModel> getAllEvents();
    Mono<EventModel> addEvent(EventModel event);
    Mono<Boolean> deleteEventById(String eventId);
    Flux<Boolean> deleteEvents(List<String> ids);
}
