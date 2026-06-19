package com.musicstream.domain.playlist.repository;

import com.musicstream.domain.playlist.model.Playlist;

import java.util.List;
import java.util.Optional;

/**
 * Contrato do repositório de Playlists.
 * Intention Revealing Interface.
 */
public interface PlaylistRepository {

    Playlist save(Playlist playlist);

    Optional<Playlist> findById(Long id);

    List<Playlist> findByAccountId(Long accountId);
}
