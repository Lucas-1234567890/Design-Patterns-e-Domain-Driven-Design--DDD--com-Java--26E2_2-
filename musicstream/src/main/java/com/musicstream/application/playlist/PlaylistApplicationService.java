package com.musicstream.application.playlist;

import com.musicstream.domain.music.model.Music;
import com.musicstream.domain.music.repository.MusicRepository;
import com.musicstream.domain.playlist.model.Playlist;
import com.musicstream.domain.playlist.repository.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Application Service para operações de Playlist.
 */
@Service
@RequiredArgsConstructor
public class PlaylistApplicationService {

    private final PlaylistRepository playlistRepository;
    private final MusicRepository musicRepository;

    /**
     * Cria uma nova playlist vazia para o usuário.
     */
    @Transactional
    public Playlist createPlaylist(Long accountId, String name) {
        Playlist playlist = Playlist.create(accountId, name);
        return playlistRepository.save(playlist);
    }

    /**
     * Adiciona uma música a uma playlist existente.
     */
    @Transactional
    public Playlist addMusicToPlaylist(Long playlistId, Long musicId) {
        Playlist playlist = findPlaylistOrThrow(playlistId);
        Music music = musicRepository.findById(musicId)
                .orElseThrow(() -> new IllegalArgumentException("Música não encontrada com id: " + musicId));
        playlist.addMusic(music);
        return playlistRepository.save(playlist);
    }

    /**
     * Remove uma música de uma playlist.
     */
    @Transactional
    public Playlist removeMusicFromPlaylist(Long playlistId, Long musicId) {
        Playlist playlist = findPlaylistOrThrow(playlistId);
        playlist.removeMusic(musicId);
        return playlistRepository.save(playlist);
    }

    /**
     * Lista todas as playlists de um usuário.
     */
    @Transactional(readOnly = true)
    public List<Playlist> listPlaylistsByAccount(Long accountId) {
        return playlistRepository.findByAccountId(accountId);
    }

    private Playlist findPlaylistOrThrow(Long playlistId) {
        return playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist não encontrada com id: " + playlistId));
    }
}
