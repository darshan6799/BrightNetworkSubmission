package com.google;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

/** A class used to represent a Video Library. */
class VideoLibrary {

  private final HashMap<String, Video> videos;
  private final HashMap<String, List<Video>> searchIndex;
  private final HashMap<String, List<Video>> tagIndex;

  VideoLibrary() {
    this.videos = new HashMap<>();
    searchIndex = new HashMap<>();
    tagIndex = new HashMap<>();
    try {
      File file = new File(this.getClass().getResource("/videos.txt").getFile());

      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] split = line.split("\\|");
        String title = split[0].strip();
        String id = split[1].strip();
        List<String> tags;
        if (split.length > 2) {
          tags = Arrays.stream(split[2].split(",")).map(String::strip).collect(toList());
        } else {
          tags = new ArrayList<>();
        }
        Video video = new Video(title, id, tags);
        this.videos.put(id, video);
        getNormalizedKeywords(video)
            .forEach(
                keyword ->
                    searchIndex.computeIfAbsent(keyword, (k) -> new ArrayList<>()).add(video));
        getNormalizedTags(video)
            .forEach(tag -> tagIndex.computeIfAbsent(tag, (k) -> new ArrayList<>()).add(video));
      }
    } catch (FileNotFoundException e) {
      System.out.println("Couldn't find videos.txt");
      e.printStackTrace();
    }
  }

  List<Video> getVideos() {
    return new ArrayList<>(this.videos.values());
  }

  Video getVideo(String videoId) {
    return this.videos.get(videoId);
  }

  List<Video> search(String searchTerm) {
    return List.copyOf(searchIndex.getOrDefault(searchTerm.toLowerCase(Locale.ROOT), List.of()));
  }

  List<Video> searchByTag(String tag) {
    return List.copyOf(tagIndex.getOrDefault(tag.toLowerCase(Locale.ROOT), List.of()));
  }

  private static final Set<String> getNormalizedKeywords(Video video) {
    var keywords = new HashSet<String>();
    keywords.addAll(List.of(video.getTitle().toLowerCase(Locale.ROOT).split(" ")));
    keywords.addAll(video.getTags().stream().map(tag -> tag.substring(1)).collect(toList()));
    return keywords;
  }

  private static final Set<String> getNormalizedTags(Video video) {
    return video.getTags().stream()
        .map(tag -> tag.toLowerCase(Locale.ROOT))
        .collect(toUnmodifiableSet());
  }
}
