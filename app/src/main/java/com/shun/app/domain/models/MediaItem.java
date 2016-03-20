package com.shun.app.domain.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public abstract class MediaItem implements Parcelable {
  private int id;
  private String title;
  private String tagline;
  private Date releaseDate;
  private String overview;
  private String posterPath;
  private String backdropPath;

  public MediaItem() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTagline() {
    return tagline;
  }

  public void setTagline(String tagline) {
    this.tagline = tagline;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
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
    dest.writeInt(this.id);
    dest.writeString(this.title);
    dest.writeString(this.tagline);
    dest.writeLong(releaseDate != null ? releaseDate.getTime() : -1);
    dest.writeString(this.overview);
    dest.writeString(this.posterPath);
    dest.writeString(this.backdropPath);
  }

  protected MediaItem(Parcel in) {
    this.id = in.readInt();
    this.title = in.readString();
    this.tagline = in.readString();
    long tmpReleaseDate = in.readLong();
    this.releaseDate = tmpReleaseDate == -1 ? null : new Date(tmpReleaseDate);
    this.overview = in.readString();
    this.posterPath = in.readString();
    this.backdropPath = in.readString();
  }
}
