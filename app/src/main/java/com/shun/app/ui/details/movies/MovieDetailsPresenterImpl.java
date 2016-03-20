package com.shun.app.ui.details.movies;

import android.support.annotation.NonNull;
import com.shun.app.domain.catalog.CatalogManager;
import com.shun.app.domain.models.Movie;
import com.shun.app.ui.common.BaseFragmentPresenter;
import com.shun.app.ui.viewmodels.MovieViewModel;
import org.greenrobot.eventbus.EventBus;

public class MovieDetailsPresenterImpl extends BaseFragmentPresenter<MovieDetailsView>
    implements MovieDetailsPresenter {
  private CatalogManager catalogManager;
  private Movie movie;

  public MovieDetailsPresenterImpl(@NonNull MovieDetailsView view, EventBus eventBus,
      CatalogManager catalogManager) {
    super(view, eventBus);
    this.catalogManager = catalogManager;
  }

  @Override public Movie getMovie() {
    return movie;
  }

  @Override public void setMovie(Movie movie) {
    this.movie = movie;
  }

  @Override public void onCreated() {
    super.onCreated();

    MovieDetailsView view = getView();

    if (movie != null && view != null) {
      MovieViewModel viewModel = new MovieViewModel(movie);
      view.setBackgroundUri(viewModel.getBackdropUri());
      view.setMovie(viewModel);

      /*catalogManager.movieTrailer(movie)
          .subscribe(video -> {
            VideoViewModel trailer = new VideoViewModel(video);
            view.setTrailer(trailer);
          });*/
    }
  }
}
