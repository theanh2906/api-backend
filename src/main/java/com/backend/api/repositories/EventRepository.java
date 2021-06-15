package com.backend.api.repositories;

import com.backend.api.models.EventModel;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends R2dbcRepository<EventModel, String> {
}
