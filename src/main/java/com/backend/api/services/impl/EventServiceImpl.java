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

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {
    private final static Logger log = LoggerFactory.getLogger(EventServiceImpl.class);
    @Autowired
    private EventRepository eventRepository;

    @Override
    public Flux<EventModel> getAllEvents() {
        return eventRepository.findAll().doOnError(throwable -> throwable.getMessage());
    }

    @Override
    public Mono<EventModel> addEvent(EventModel event) {
        event.setId(UUID.randomUUID().toString());
        return eventRepository
                .save(event)
                .switchIfEmpty(Mono.error(new RuntimeException("Could not add event")));
    }

    @Override
    public Mono<Boolean> deleteEventById(String eventId) {
        return eventRepository
                .findById(eventId)
                .flatMap(found -> {
                    eventRepository.deleteById(eventId).subscribe();
                    return Mono.just(Boolean.TRUE);
                })
                .doOnError(throwable -> new RuntimeException("Cannot find event"));
    }

    @Override
    public Flux<Boolean> deleteEvents(List<String> ids) {
        return eventRepository
                .findAllById(ids)
                .flatMap(found -> {
                    eventRepository.delete(found).subscribe();
                    return Flux.just(Boolean.TRUE);
                })
                .switchIfEmpty(Flux.just(Boolean.FALSE));
    }
}
