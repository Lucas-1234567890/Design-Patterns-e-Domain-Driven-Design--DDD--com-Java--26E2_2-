package com.musicstream.infrastructure.persistence.playlist;

import com.musicstream.domain.playlist.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistJpaRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findByAccountId(Long accountId);
}
