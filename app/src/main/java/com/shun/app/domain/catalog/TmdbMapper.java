package com.shun.app.domain.catalog;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.shun.app.domain.models.Movie;
import com.shun.app.domain.models.Video;
import com.uwetrottmann.tmdb.entities.MovieResultsPage;
import com.uwetrottmann.tmdb.entities.Videos;
import java.util.List;

public final class TmdbMapper {
  public static List<Movie> mapMovieResultsPage(@NonNull MovieResultsPage resultsPage) {
    return Stream.of(resultsPage.results)
        .map(TmdbMapper::mapMovie)
        .collect(Collectors.toList());
  }

  public static Movie mapMovie(@NonNull com.uwetrottmann.tmdb.entities.Movie value) {
    Movie movie = new Movie();
    movie.setId(value.id);
    movie.setTitle(value.title);
    movie.setTagline(value.tagline);
    movie.setReleaseDate(value.release_date);
    movie.setOverview(value.overview);
    movie.setPosterPath(value.poster_path);
    movie.setBackdropPath(value.backdrop_path);
    return movie;
  }

  @Nullable public static Video mapVideo(
      @NonNull Optional<com.uwetrottmann.tmdb.entities.Videos.Video> value) {
    Videos.Video source = value.get();

    if (source == null) {
      return null;
    }

    return mapVideo(source);
  }

  public static Video mapVideo(com.uwetrottmann.tmdb.entities.Videos.Video value) {
    Video video = new Video();
    video.setId(value.id);
    video.setKey(value.key);
    video.setName(value.name);
    video.setSite(value.site);
    video.setSize(value.size);
    video.setType(value.type);
    return video;
  }
}
