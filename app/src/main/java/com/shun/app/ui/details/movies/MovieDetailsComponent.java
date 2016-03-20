package com.shun.app.ui.details.movies;

import com.shun.app.AppComponent;
import com.shun.app.ui.ActivityScope;
import dagger.Component;

@ActivityScope
@Component(
    dependencies = {
        AppComponent.class
    },
    modules = MovieDetailsModule.class)
public interface MovieDetailsComponent {
  void inject(MovieDetailsFragment fragment);

  MovieDetailsPresenter getMovieDetailsPresenter();
}
