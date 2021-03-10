package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;

public interface EventRepository {
    Event find(long id);
    void save(Event event);
}
