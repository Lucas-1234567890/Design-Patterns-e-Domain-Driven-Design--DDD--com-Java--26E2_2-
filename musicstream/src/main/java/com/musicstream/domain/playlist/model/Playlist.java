package com.musicstream.domain.playlist.model;

import com.musicstream.domain.music.model.Music;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Aggregate Root do contexto de Playlist.
 * Controla o acesso e mutação das músicas da playlist.
 * Linguagem ubíqua: Playlist
 */
@Entity
@Table(name = "playlists")
@Getter
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "playlist_musics",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "music_id")
    )
    private List<Music> musics = new ArrayList<>();

    protected Playlist() {
        // Necessário para JPA
    }

    /**
     * Cria uma nova playlist vazia para uma conta.
     */
    public static Playlist create(Long accountId, String name) {
        if (accountId == null) throw new IllegalArgumentException("accountId não pode ser nulo.");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome da playlist não pode ser vazio.");

        Playlist playlist = new Playlist();
        playlist.accountId = accountId;
        playlist.name = name;
        return playlist;
    }

    /**
     * Adiciona uma música à playlist se ela ainda não estiver presente.
     */
    public void addMusic(Music music) {
        if (music == null) throw new IllegalArgumentException("Música não pode ser nula.");
        boolean alreadyInPlaylist = musics.stream().anyMatch(m -> m.getId().equals(music.getId()));
        if (!alreadyInPlaylist) {
            musics.add(music);
        }
    }

    /**
     * Remove uma música da playlist pelo id.
     */
    public void removeMusic(Long musicId) {
        musics.removeIf(m -> m.getId().equals(musicId));
    }

    /**
     * Retorna a lista de músicas como coleção imutável.
     */
    public List<Music> getMusics() {
        return Collections.unmodifiableList(musics);
    }

    /**
     * Retorna o total de músicas na playlist.
     */
    public int totalMusics() {
        return musics.size();
    }
}
