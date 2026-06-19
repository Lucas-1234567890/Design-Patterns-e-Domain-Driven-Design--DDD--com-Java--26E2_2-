package com.musicstream.infrastructure.persistence.music;

import com.musicstream.domain.music.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicJpaRepository extends JpaRepository<Music, Long> {
}
