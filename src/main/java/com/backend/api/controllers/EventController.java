package com.backend.api.controllers;

import com.backend.api.dtos.EventDto;
import com.backend.api.dtos.ResponseDto;
import com.backend.api.mappers.EventMapper;
import com.backend.api.models.EventModel;
import com.backend.api.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping("")
    public Flux<EventDto> getAllEvents() {
        return eventService
                .getAllEvents()
                .map(EventMapper::toDto);
    }

    @PostMapping("")
    public Mono<ResponseDto> addEvent(@RequestBody EventDto dto) {
        final EventModel model = EventMapper.toModel(dto);
        return eventService
                .addEvent(model)
                .map(eventModel -> new ResponseDto("Successfully add new event" + eventModel.getTitle()))
                .doOnError(throwable -> new ResponseDto(throwable.getMessage()));
    }
}
