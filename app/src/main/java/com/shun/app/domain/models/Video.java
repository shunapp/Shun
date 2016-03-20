package com.shun.app.domain.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Video implements Parcelable {
  public final static String TYPE_TRAILER = "trailer";
  public final static String SITE_YOUTUBE = "youtube";

  private String id;
  private String key;
  private String name;
  private String site;
  private int size;
  private String type;

  public Video() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSite() {
    return site;
  }

  public void setSite(String site) {
    this.site = site;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.id);
    dest.writeString(this.key);
    dest.writeString(this.name);
    dest.writeString(this.site);
    dest.writeInt(this.size);
    dest.writeString(this.type);
  }

  protected Video(Parcel in) {
    this.id = in.readString();
    this.key = in.readString();
    this.name = in.readString();
    this.site = in.readString();
    this.size = in.readInt();
    this.type = in.readString();
  }

  public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
    @Override public Video createFromParcel(Parcel source) {
      return new Video(source);
    }

    @Override public Video[] newArray(int size) {
      return new Video[size];
    }
  };
}
