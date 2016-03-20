package com.shun.app.ui.viewmodels;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.shun.app.domain.models.MediaItem;
import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class MediaItemViewModel {
  private MediaItem mediaItem;
  private Calendar releaseDate;

  public MediaItemViewModel(@NonNull MediaItem mediaItem) {
    this.mediaItem = mediaItem;

    releaseDate = new GregorianCalendar();
    releaseDate.setTime(mediaItem.getReleaseDate());
  }

  public MediaItem getMediaItem() {
    return mediaItem;
  }

  public String getTitle() {
    return mediaItem.getTitle();
  }

  public String getTagline() {
    return mediaItem.getTagline();
  }

  public String getOverview() {
    return mediaItem.getOverview();
  }

  public int getReleaseYear() {
    return releaseDate.get(Calendar.YEAR);
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
