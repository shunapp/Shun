package com.shun.app.domain.catalog;

import android.support.annotation.NonNull;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.shun.app.domain.models.Movie;
import com.shun.app.domain.models.Video;
import com.uwetrottmann.tmdb.Tmdb;
import com.uwetrottmann.tmdb.entities.Videos;
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

  @Override public Observable<Video> movieTrailer(Movie movie) {
    return tmdbManager.moviesService()
        .videos(movie.getId(), null)
        .map(TmdbCatalogManager::findYouTubeTrailer)
        .map(TmdbMapper::mapVideo);
  }

  @Override public Observable<List<Movie>> searchMovies(String query) {
    return searchMovies(query, 1);
  }

  @Override public Observable<List<Movie>> searchMovies(String query, int page) {
    return tmdbManager.searchService()
        .movie(query, page, null, null, null, null, null)
        .map(TmdbMapper::mapMovieResultsPage);
  }

  private static Optional<Videos.Video> findYouTubeTrailer(@NonNull Videos videos) {
    return Stream.of(videos.results)
        .filter(
            video -> video.type.equals(Video.TYPE_TRAILER) && video.site.equals(Video.SITE_YOUTUBE))
        .findFirst();
  }
}