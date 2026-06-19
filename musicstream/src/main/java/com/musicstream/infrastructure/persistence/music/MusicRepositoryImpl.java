package com.musicstream.infrastructure.persistence.music;

import com.musicstream.domain.music.model.FavoriteMusic;
import com.musicstream.domain.music.model.Music;
import com.musicstream.domain.music.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementação concreta do repositório de Músicas.
 */
@Repository
@RequiredArgsConstructor
public class MusicRepositoryImpl implements MusicRepository {

    private final MusicJpaRepository musicJpaRepository;
    private final FavoriteMusicJpaRepository favoriteMusicJpaRepository;

    @Override
    public Music save(Music music) {
        return musicJpaRepository.save(music);
    }

    @Override
    public Optional<Music> findById(Long id) {
        return musicJpaRepository.findById(id);
    }

    @Override
    public List<Music> findAll() {
        return musicJpaRepository.findAll();
    }

    @Override
    public FavoriteMusic saveFavorite(FavoriteMusic favoriteMusic) {
        return favoriteMusicJpaRepository.save(favoriteMusic);
    }

    @Override
    public List<FavoriteMusic> findFavoritesByAccountId(Long accountId) {
        return favoriteMusicJpaRepository.findByAccountId(accountId);
    }

    @Override
    public boolean isMusicAlreadyFavoritedByAccount(Long accountId, Long musicId) {
        return favoriteMusicJpaRepository.existsByAccountIdAndMusicId(accountId, musicId);
    }
}
