package com.shun.app.ui.details.movies;

import com.shun.app.domain.models.Movie;
import com.shun.app.ui.common.FragmentPresenter;

public interface MovieDetailsPresenter extends FragmentPresenter {
  Movie getMovie();

  void setMovie(Movie movie);
}
