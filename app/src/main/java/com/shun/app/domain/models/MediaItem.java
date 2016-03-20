package com.shun.app.domain.models;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class MediaItem implements Parcelable {
  private String title;
  private String overview;
  private String posterPath;
  private String backdropPath;

  public MediaItem() {
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public String getBackdropPath() {
    return backdropPath;
  }

  public void setBackdropPath(String backdropPath) {
    this.backdropPath = backdropPath;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.title);
    dest.writeString(this.overview);
    dest.writeString(this.posterPath);
    dest.writeString(this.backdropPath);
  }

  protected MediaItem(Parcel in) {
    this.title = in.readString();
    this.overview = in.readString();
    this.posterPath = in.readString();
    this.backdropPath = in.readString();
  }
}
