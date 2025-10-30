package com.aurawave.service;

import com.aurawave.dao.SessionDao;
import com.aurawave.domain.model.Session;
import com.aurawave.dto.sessionDto.SessionRequestDto;
import com.aurawave.dto.sessionDto.SessionResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionDao sessionDao;
    private final ModelMapper mapper;

    public SessionResponseDto create(SessionRequestDto dto) {
        Session session = mapper.map(dto, Session.class);
        Long id = sessionDao.create(session);
        Session saved = sessionDao.getById(id);
        return mapper.map(saved, SessionResponseDto.class);
    }

    public SessionResponseDto update(Long id, SessionRequestDto dto) {
        Session session = mapper.map(dto, Session.class);
        sessionDao.update(id, session);
        Session updated = sessionDao.getById(id);
        return mapper.map(updated, SessionResponseDto.class);
    }

    public SessionResponseDto getById(Long id) {
        return mapper.map(sessionDao.getById(id), SessionResponseDto.class);
    }

    public List<SessionResponseDto> getAll() {
        return sessionDao.getAll().stream()
                .map(s -> mapper.map(s, SessionResponseDto.class))
                .toList();
    }

    public void delete(Long id) {
        sessionDao.delete(id);
    }
}
