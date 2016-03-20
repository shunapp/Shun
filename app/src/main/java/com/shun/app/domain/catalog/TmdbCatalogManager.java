package com.shun.app.domain.catalog;

import com.shun.app.domain.models.Movie;
import com.uwetrottmann.tmdb.Tmdb;
import java.util.List;
import rx.Observable;

public class TmdbCatalogManager implements CatalogManager {
  private Tmdb tmdbManager;

  public TmdbCatalogManager(Tmdb tmdbManager) {
    this.tmdbManager = tmdbManager;
  }

  @Override public Observable<List<Movie>> popularMovies() {
    return popularMovies(1);
  }

  @Override public Observable<List<Movie>> popularMovies(int page) {
    return tmdbManager.moviesService()
        .popular(page, null)
        .map(TmdbMapper::mapMovieResultsPage);
  }
}