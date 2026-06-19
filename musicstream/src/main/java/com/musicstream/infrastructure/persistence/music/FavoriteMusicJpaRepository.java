package com.musicstream.infrastructure.persistence.music;

import com.musicstream.domain.music.model.FavoriteMusic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteMusicJpaRepository extends JpaRepository<FavoriteMusic, Long> {
    List<FavoriteMusic> findByAccountId(Long accountId);
    boolean existsByAccountIdAndMusicId(Long accountId, Long musicId);
}
