package com.shun.app.domain.catalog;

import com.shun.app.domain.models.Movie;
import com.shun.app.domain.models.Video;
import java.util.List;
import rx.Observable;

public interface CatalogManager {
  Observable<List<Movie>> popularMovies();

  Observable<List<Movie>> popularMovies(int page);

  Observable<Video> movieTrailer(Movie movie);

  Observable<List<Movie>> searchMovies(String query);

  Observable<List<Movie>> searchMovies(String query, int page);
}
