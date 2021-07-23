package com.backend.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private String id;
    private String title;
    @JsonProperty("start")
    private String startDate;
    @JsonProperty("end")
    private String endDate;
    private String backgroundColor;
    @JsonProperty("color")
    private String textColor;
    private String borderColor;
    private Boolean allDay;
    private String url;
}
