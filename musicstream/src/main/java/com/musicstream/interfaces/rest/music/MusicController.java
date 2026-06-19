package com.musicstream.interfaces.rest.music;

import com.musicstream.application.music.MusicApplicationService;
import com.musicstream.domain.music.model.FavoriteMusic;
import com.musicstream.domain.music.model.Music;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para operações de Música e Favoritos.
 * Parte 1 - Operação 3: Favoritar Músicas.
 */
@RestController
@RequestMapping("/api/musics")
@RequiredArgsConstructor
public class MusicController {

    private final MusicApplicationService musicApplicationService;

    /**
     * POST /api/musics
     * Cadastra uma nova música no catálogo.
     */
    @PostMapping
    public ResponseEntity<Music> addMusic(@RequestBody AddMusicRequest request) {
        Music music = musicApplicationService.addMusic(
                request.title(),
                request.artist(),
                request.genre(),
                request.durationInSeconds()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(music);
    }

    /**
     * GET /api/musics
     * Lista todas as músicas do catálogo.
     */
    @GetMapping
    public ResponseEntity<List<Music>> listAllMusics() {
        return ResponseEntity.ok(musicApplicationService.listAllMusics());
    }

    /**
     * POST /api/musics/{musicId}/favorite
     * Adiciona uma música à lista de favoritos do usuário.
     */
    @PostMapping("/{musicId}/favorite")
    public ResponseEntity<FavoriteMusic> favoriteMusic(
            @PathVariable Long musicId,
            @RequestBody FavoriteMusicRequest request) {
        FavoriteMusic favorite = musicApplicationService.favoriteMusic(request.accountId(), musicId);
        return ResponseEntity.status(HttpStatus.CREATED).body(favorite);
    }

    /**
     * GET /api/musics/favorites/{accountId}
     * Lista as músicas favoritas de um usuário.
     */
    @GetMapping("/favorites/{accountId}")
    public ResponseEntity<List<FavoriteMusic>> listFavorites(@PathVariable Long accountId) {
        return ResponseEntity.ok(musicApplicationService.listFavorites(accountId));
    }

    public record AddMusicRequest(String title, String artist, String genre, Integer durationInSeconds) {}

    public record FavoriteMusicRequest(Long accountId) {}
}
