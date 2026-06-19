package com.musicstream.domain.music.model;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * Entidade que representa uma Música no catálogo do sistema.
 * Linguagem ubíqua: Musica (Music)
 */
@Entity
@Table(name = "musics")
@Getter
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artist;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private Integer durationInSeconds;

    protected Music() {
        // Necessário para JPA
    }

    /**
     * Cria uma nova música com todos os dados obrigatórios.
     */
    public static Music create(String title, String artist, String genre, Integer durationInSeconds) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Título não pode ser vazio.");
        if (artist == null || artist.isBlank()) throw new IllegalArgumentException("Artista não pode ser vazio.");
        if (genre == null || genre.isBlank()) throw new IllegalArgumentException("Gênero não pode ser vazio.");
        if (durationInSeconds == null || durationInSeconds <= 0) throw new IllegalArgumentException("Duração deve ser positiva.");

        Music music = new Music();
        music.title = title;
        music.artist = artist;
        music.genre = genre;
        music.durationInSeconds = durationInSeconds;
        return music;
    }

    /**
     * Retorna a duração da música formatada como mm:ss.
     */
    public String getFormattedDuration() {
        int minutes = durationInSeconds / 60;
        int seconds = durationInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
