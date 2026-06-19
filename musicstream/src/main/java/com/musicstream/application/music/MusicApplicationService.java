package com.musicstream.application.music;

import com.musicstream.domain.account.exception.AccountNotFoundException;
import com.musicstream.domain.account.repository.AccountRepository;
import com.musicstream.domain.music.model.FavoriteMusic;
import com.musicstream.domain.music.model.Music;
import com.musicstream.domain.music.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Application Service para operações de Música e Favoritos.
 */
@Service
@RequiredArgsConstructor
public class MusicApplicationService {

    private final MusicRepository musicRepository;
    private final AccountRepository accountRepository;

    /**
     * Cadastra uma nova música no catálogo.
     */
    @Transactional
    public Music addMusic(String title, String artist, String genre, Integer durationInSeconds) {
        Music music = Music.create(title, artist, genre, durationInSeconds);
        return musicRepository.save(music);
    }

    /**
     * Adiciona uma música à lista de favoritos de um usuário.
     */
    @Transactional
    public FavoriteMusic favoriteMusic(Long accountId, Long musicId) {
        accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        Music music = musicRepository.findById(musicId)
                .orElseThrow(() -> new IllegalArgumentException("Música não encontrada com id: " + musicId));

        if (musicRepository.isMusicAlreadyFavoritedByAccount(accountId, musicId)) {
            throw new IllegalStateException("Música já está na lista de favoritos.");
        }

        FavoriteMusic favoriteMusic = FavoriteMusic.create(accountId, music);
        return musicRepository.saveFavorite(favoriteMusic);
    }

    /**
     * Lista as músicas favoritas de um usuário.
     */
    @Transactional(readOnly = true)
    public List<FavoriteMusic> listFavorites(Long accountId) {
        return musicRepository.findFavoritesByAccountId(accountId);
    }

    /**
     * Lista todas as músicas do catálogo.
     */
    @Transactional(readOnly = true)
    public List<Music> listAllMusics() {
        return musicRepository.findAll();
    }
}
