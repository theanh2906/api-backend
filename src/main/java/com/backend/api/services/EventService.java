package com.backend.api.services;

import com.backend.api.dtos.EventDto;
import com.backend.api.models.EventModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventService {
    Flux<EventModel> getAllEvents();
    Mono<EventModel> addEvent(EventModel event);
}
