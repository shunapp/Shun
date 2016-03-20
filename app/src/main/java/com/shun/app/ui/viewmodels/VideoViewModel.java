package com.shun.app.ui.viewmodels;

import android.support.annotation.NonNull;
import com.shun.app.domain.models.Video;

public class VideoViewModel {
  private Video video;

  public VideoViewModel(@NonNull Video video) {
    this.video = video;
  }

  public String getKey() {
    return video.getKey();
  }
}
