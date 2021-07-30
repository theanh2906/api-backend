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

import java.util.Comparator;
import java.util.List;

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
                .map(EventMapper::toDto).sort(Comparator.comparing(EventDto::getStartDate));
    }

    @PostMapping("")
    public Mono<ResponseDto> addEvents(@RequestBody EventDto dto) {
        final EventModel model = EventMapper.toModel(dto);
        return eventService
                .addEvent(model)
                .map(eventModel -> new ResponseDto("Successfully add new event" + eventModel.getTitle()))
                .doOnError(throwable -> new ResponseDto(throwable.getMessage()));
    }

    @ResponseBody
    @PostMapping("/delete")
    public Mono<ResponseDto> deleteEvents(@RequestBody List<String> dtoList) {
        return eventService
                .deleteEvents(dtoList)
                .collectList()
                .filter(booleans -> !booleans.contains(Boolean.FALSE))
                .map(success -> new ResponseDto("Successfully delete events"))
                .switchIfEmpty(Mono.just(new ResponseDto("Fail to delete. Cannot find events")));
    }
    @DeleteMapping("/{id}")
    public Mono<ResponseDto> deleteEventById(@PathVariable String id) {
        return eventService
                .deleteEventById(id)
                .map(success -> new ResponseDto("Successfully delete event " + id))
                .switchIfEmpty(Mono.just(new ResponseDto("Fail to delete. Cannot find event")));
    }
}
