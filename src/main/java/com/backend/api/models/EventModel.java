package com.backend.api.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table("events")
public class EventModel implements Persistable<String> {
    @Id
    private String id;
    @Column
    private String title;
    @Column
    private Boolean allDay;
    @Column
    private String url;
    @Column
    private String startDate;
    @Column
    private String endDate;
    @Column
    private String backgroundColor;
    @Column
    private String textColor;
    @Column
    private String borderColor;

    @Transient
    private boolean isUpdated;

    @Override
    public boolean isNew() {
        return !isUpdated;
    }
}
