package com.shun.app.domain.catalog;

import android.support.annotation.NonNull;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.shun.app.domain.models.Movie;
import com.uwetrottmann.tmdb.entities.MovieResultsPage;
import java.util.List;

public final class TmdbMapper {
  public static List<Movie> mapMovieResultsPage(@NonNull MovieResultsPage resultsPage) {
    return Stream.of(resultsPage.results)
        .map(TmdbMapper::mapMovie)
        .collect(Collectors.toList());
  }

  public static Movie mapMovie(@NonNull com.uwetrottmann.tmdb.entities.Movie source) {
    Movie movie = new Movie();
    movie.setTitle(source.title);
    movie.setOverview(source.overview);
    movie.setPosterPath(source.poster_path);
    movie.setBackdropPath(source.backdrop_path);
    return movie;
  }
}
