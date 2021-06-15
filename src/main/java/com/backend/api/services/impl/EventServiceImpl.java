package com.backend.api.services.impl;

import com.backend.api.models.EventModel;
import com.backend.api.repositories.EventRepository;
import com.backend.api.services.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {
    private final static Logger log = LoggerFactory.getLogger(EventServiceImpl.class);
    @Autowired
    private EventRepository eventRepository;

    @Override
    public Flux<EventModel> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Mono<EventModel> addEvent(EventModel event) {
        event.setId(UUID.randomUUID().toString());
        return eventRepository
                .save(event)
                .switchIfEmpty(Mono.error(new RuntimeException("Could not add event")));
    }
}
