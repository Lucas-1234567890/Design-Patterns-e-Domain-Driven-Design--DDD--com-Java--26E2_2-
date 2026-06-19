package com.musicstream.domain.music.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Entidade que representa o favorito de uma música por um usuário.
 * Linguagem ubíqua: MusicaFavorita (FavoriteMusic)
 */
@Entity
@Table(name = "favorite_musics",
        uniqueConstraints = @UniqueConstraint(columnNames = {"account_id", "music_id"}))
@Getter
public class FavoriteMusic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "account_id")
    private Long accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id", nullable = false)
    private Music music;

    @Column(nullable = false)
    private LocalDateTime favoritedAt;

    protected FavoriteMusic() {
        // Necessário para JPA
    }

    /**
     * Favorita uma música para uma conta específica.
     */
    public static FavoriteMusic create(Long accountId, Music music) {
        if (accountId == null) throw new IllegalArgumentException("accountId não pode ser nulo.");
        if (music == null) throw new IllegalArgumentException("Música não pode ser nula.");

        FavoriteMusic favorite = new FavoriteMusic();
        favorite.accountId = accountId;
        favorite.music = music;
        favorite.favoritedAt = LocalDateTime.now();
        return favorite;
    }
}
