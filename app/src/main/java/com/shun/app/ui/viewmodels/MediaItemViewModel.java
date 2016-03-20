package com.shun.app.ui.viewmodels;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.shun.app.domain.models.MediaItem;

public abstract class MediaItemViewModel {
  private MediaItem mediaItem;

  public MediaItemViewModel(@NonNull MediaItem mediaItem) {
    this.mediaItem = mediaItem;
  }

  public String getTitle() {
    return mediaItem.getTitle();
  }

  public String getOverview() {
    return mediaItem.getOverview();
  }

  @Nullable public Uri getPosterUri() {
    String path = mediaItem.getPosterPath();

    if (TextUtils.isEmpty(path)) {
      return null;
    }

    return Uri.parse("http://image.tmdb.org/t/p/w500/" + path);
  }

  @Nullable public Uri getBackdropUri() {
    String path = mediaItem.getBackdropPath();

    if (TextUtils.isEmpty(path)) {
      return null;
    }

    return Uri.parse("http://image.tmdb.org/t/p/original/" + path);
  }
}
