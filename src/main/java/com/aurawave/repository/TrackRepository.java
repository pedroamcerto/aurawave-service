package com.aurawave.repository;

import com.aurawave.core.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrackRepository extends JpaRepository<Track, UUID> { }
