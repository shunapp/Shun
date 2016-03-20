package com.shun.app.ui.events;

import com.shun.app.domain.models.MediaItem;

public class ShowMediaItemDetails {
  private MediaItem mediaItem;

  public ShowMediaItemDetails(MediaItem mediaItem) {
    this.mediaItem = mediaItem;
  }

  public MediaItem getMediaItem() {
    return mediaItem;
  }
}
