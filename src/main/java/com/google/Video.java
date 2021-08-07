package com.google;

import static java.util.stream.Collectors.joining;

import java.util.Collections;
import java.util.List;

/** A class used to represent a video. */
class Video {

  private final String title;
  private final String videoId;
  private final List<String> tags;

  Video(String title, String videoId, List<String> tags) {
    this.title = title;
    this.videoId = videoId;
    this.tags = Collections.unmodifiableList(tags);
  }

  String getTitle() {
    return title;
  }

  String getVideoId() {
    return videoId;
  }

  List<String> getTags() {
    return tags;
  }

  @Override
  public String toString() {
    var tagString = tags.stream().collect(joining(" "));
    return String.format("%s (%s) [%s]", title, videoId, tagString);
  }
}
