package com.shun.app.domain.catalog;

import com.shun.app.domain.models.Movie;
import java.util.List;
import rx.Observable;

public interface CatalogManager {
  Observable<List<Movie>> popularMovies(int page);

  Observable<List<Movie>> popularMovies();
}
