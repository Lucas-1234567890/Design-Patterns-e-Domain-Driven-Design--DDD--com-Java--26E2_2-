package com.musicstream.domain.music.repository;

import com.musicstream.domain.music.model.FavoriteMusic;
import com.musicstream.domain.music.model.Music;

import java.util.List;
import java.util.Optional;

/**
 * Contrato do repositório de Músicas e Favoritos.
 * Intention Revealing Interface.
 */
public interface MusicRepository {

    Music save(Music music);

    Optional<Music> findById(Long id);

    List<Music> findAll();

    FavoriteMusic saveFavorite(FavoriteMusic favoriteMusic);

    List<FavoriteMusic> findFavoritesByAccountId(Long accountId);

    boolean isMusicAlreadyFavoritedByAccount(Long accountId, Long musicId);
}
