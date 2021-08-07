package com.google;

import static java.util.Locale.ROOT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VideoPlaylistStore {

  private final Map<String, VideoPlaylist> playlists = new HashMap<>();

  public Optional<VideoPlaylist> getPlaylist(String playlistName) {
    return Optional.ofNullable(playlists.get(getPlaylistId(playlistName)));
  }

  public void deletePlaylist(String playlistName) {
    playlists.remove(getPlaylistId(playlistName));
  }

  public void savePlaylist(VideoPlaylist playlist) {
    playlists.put(getPlaylistId(playlist.getPlaylistName()), playlist);
  }

  private String getPlaylistId(String playlistName) {
    return playlistName.toUpperCase(ROOT);
  }

  public List<VideoPlaylist> getPlaylists() {
    // List.copyOf() is used to create a defensive, immutable copy of all the playlists.
    return List.copyOf(playlists.values());
  }
}
