package com.musicstream.infrastructure.persistence.playlist;

import com.musicstream.domain.playlist.model.Playlist;
import com.musicstream.domain.playlist.repository.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PlaylistRepositoryImpl implements PlaylistRepository {

    private final PlaylistJpaRepository jpaRepository;

    @Override
    public Playlist save(Playlist playlist) {
        return jpaRepository.save(playlist);
    }

    @Override
    public Optional<Playlist> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Playlist> findByAccountId(Long accountId) {
        return jpaRepository.findByAccountId(accountId);
    }
}
