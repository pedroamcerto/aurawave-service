package com.aurawave.service;

import com.aurawave.dao.EventDao;
import com.aurawave.domain.model.Event;
import com.aurawave.dto.eventDto.EventRequestDto;
import com.aurawave.dto.eventDto.EventResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventDao eventDao;
    private final ModelMapper mapper;

    public EventResponseDto create(EventRequestDto dto) {
        Event event = mapper.map(dto, Event.class);
        Long id = eventDao.create(event);
        Event saved = eventDao.getById(id);
        return mapper.map(saved, EventResponseDto.class);
    }

    public EventResponseDto update(Long id, EventRequestDto dto) {
        Event event = mapper.map(dto, Event.class);
        eventDao.update(id, event);
        Event updated = eventDao.getById(id);
        return mapper.map(updated, EventResponseDto.class);
    }

    public EventResponseDto getById(Long id) {
        return mapper.map(eventDao.getById(id), EventResponseDto.class);
    }

    public List<EventResponseDto> getAll() {
        return eventDao.getAll().stream()
                .map(e -> mapper.map(e, EventResponseDto.class))
                .toList();
    }

    public void delete(Long id) {
        eventDao.delete(id);
    }
}
