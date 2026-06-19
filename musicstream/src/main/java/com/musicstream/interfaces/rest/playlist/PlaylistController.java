package com.musicstream.interfaces.rest.playlist;

import com.musicstream.application.playlist.PlaylistApplicationService;
import com.musicstream.domain.playlist.model.Playlist;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para operações de Playlist.
 * Parte 1 - Operação 4: Playlist.
 */
@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistApplicationService playlistApplicationService;

    /**
     * POST /api/playlists
     * Cria uma nova playlist para o usuário.
     */
    @PostMapping
    public ResponseEntity<Playlist> createPlaylist(@RequestBody CreatePlaylistRequest request) {
        Playlist playlist = playlistApplicationService.createPlaylist(request.accountId(), request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(playlist);
    }

    /**
     * GET /api/playlists/account/{accountId}
     * Lista todas as playlists de um usuário.
     */
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Playlist>> listByAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(playlistApplicationService.listPlaylistsByAccount(accountId));
    }

    /**
     * POST /api/playlists/{playlistId}/musics/{musicId}
     * Adiciona uma música a uma playlist.
     */
    @PostMapping("/{playlistId}/musics/{musicId}")
    public ResponseEntity<Playlist> addMusicToPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long musicId) {
        Playlist playlist = playlistApplicationService.addMusicToPlaylist(playlistId, musicId);
        return ResponseEntity.ok(playlist);
    }

    /**
     * DELETE /api/playlists/{playlistId}/musics/{musicId}
     * Remove uma música de uma playlist.
     */
    @DeleteMapping("/{playlistId}/musics/{musicId}")
    public ResponseEntity<Playlist> removeMusicFromPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long musicId) {
        Playlist playlist = playlistApplicationService.removeMusicFromPlaylist(playlistId, musicId);
        return ResponseEntity.ok(playlist);
    }

    public record CreatePlaylistRequest(Long accountId, String name) {}
}
