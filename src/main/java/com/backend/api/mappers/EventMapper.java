package com.backend.api.mappers;

import com.backend.api.dtos.EventDto;
import com.backend.api.models.EventModel;
import org.springframework.beans.BeanUtils;

public class EventMapper {
    public static EventDto toDto(EventModel model) {
        final EventDto dto = new EventDto();
        BeanUtils.copyProperties(model, dto);
        return dto;
    }
    public static EventModel toModel(EventDto dto) {
        final EventModel model = new EventModel();
        BeanUtils.copyProperties(dto, model);
        return model;
    }
}
