package com.shun.app.domain.models;

import android.os.Parcel;

public class Movie extends MediaItem {
  public Movie() {
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
  }

  protected Movie(Parcel in) {
    super(in);
  }

  public static final Creator<Movie> CREATOR = new Creator<Movie>() {
    @Override public Movie createFromParcel(Parcel source) {
      return new Movie(source);
    }

    @Override public Movie[] newArray(int size) {
      return new Movie[size];
    }
  };
}
