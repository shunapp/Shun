package com.shun.app.ui.details.movies;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.SparseArrayObjectAdapter;
import android.util.DisplayMetrics;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.shun.app.App;
import com.shun.app.AppComponent;
import com.shun.app.R;
import com.shun.app.domain.models.Movie;
import com.shun.app.ui.presenters.MovieDetailsDescriptionPresenter;
import com.shun.app.ui.viewmodels.MovieViewModel;
import com.shun.app.ui.viewmodels.VideoViewModel;
import com.squareup.leakcanary.RefWatcher;
import javax.inject.Inject;

public class MovieDetailsFragment extends DetailsFragment implements MovieDetailsView {
  private final static String ARGS_MOVIE = "movie";
  private final static String STATE_MOVIE = "movie";
  private final static int ACTION_WATCH = 0;
  private final static int ACTION_TRAILER = 0;

  @Inject @Nullable RefWatcher refWatcher;
  @Inject MovieDetailsPresenter presenter;

  private BackgroundManager backgroundManager;
  private DisplayMetrics displayMetrics;
  private ArrayObjectAdapter rowsAdapter;
  private SparseArrayObjectAdapter actionsAdapter;

  public static MovieDetailsFragment newInstance(Movie movie) {
    MovieDetailsFragment fragment = new MovieDetailsFragment();
    Bundle args = new Bundle();
    args.putParcelable(ARGS_MOVIE, movie);
    fragment.setArguments(args);

    return fragment;
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);

    AppComponent appComponent = App.getAppComponent(getActivity());

    if (appComponent != null) {
      DaggerMovieDetailsComponent.builder()
          .appComponent(appComponent)
          .movieDetailsModule(new MovieDetailsModule(this))
          .build()
          .inject(this);
    }

    presenter.initialize();
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    Movie movie;

    if (savedInstanceState != null) {
      movie = savedInstanceState.getParcelable(STATE_MOVIE);
    } else {
      movie = getArguments().getParcelable(ARGS_MOVIE);
    }

    if (movie == null) {
      Activity activity = getActivity();

      if (activity != null) {
        activity.finish();
      }

      return;
    }

    presenter.setMovie(movie);

    Activity activity = getActivity();
    backgroundManager = BackgroundManager.getInstance(activity);
    backgroundManager.attach(activity.getWindow());

    displayMetrics = new DisplayMetrics();
    getActivity().getWindowManager()
        .getDefaultDisplay()
        .getMetrics(displayMetrics);

    presenter.onCreated();
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    outState.putParcelable(STATE_MOVIE, presenter.getMovie());

    super.onSaveInstanceState(outState);
  }

  @Override public void onStop() {
    backgroundManager.release();
    presenter.onStop();

    super.onStop();
  }

  @Override public void onDestroy() {
    presenter.onDestroy();

    if (refWatcher != null) {
      refWatcher.watch(this);
    }

    backgroundManager = null;

    super.onDestroy();
  }

  @Override public void setMovie(MovieViewModel viewModel) {
    if (viewModel == null) {
      return;
    }

    ClassPresenterSelector selector = new ClassPresenterSelector();
    FullWidthDetailsOverviewRowPresenter rowPresenter =
        new FullWidthDetailsOverviewRowPresenter(new MovieDetailsDescriptionPresenter());

    selector.addClassPresenter(DetailsOverviewRow.class, rowPresenter);
    selector.addClassPresenter(ListRow.class, new ListRowPresenter());
    rowsAdapter = new ArrayObjectAdapter(selector);

    DetailsOverviewRow detailsOverview = new DetailsOverviewRow(viewModel);
    actionsAdapter = new SparseArrayObjectAdapter();
    String watchAction = getString(R.string.details_actions_watch);
    actionsAdapter.set(0, new Action(ACTION_WATCH, watchAction));
    detailsOverview.setActionsAdapter(actionsAdapter);

    rowsAdapter.add(detailsOverview);

    setAdapter(rowsAdapter);
  }

  @Override public void setTrailer(VideoViewModel viewModel) {
    if (viewModel != null) {
      String trailerAction = getString(R.string.details_actions_trailer);
      actionsAdapter.set(1, new Action(ACTION_TRAILER, trailerAction));
    }
  }

  @Override public void setBackgroundUri(Uri uri) {
    if (uri == null || displayMetrics == null) {
      return;
    }

    int width = displayMetrics.widthPixels;
    int height = displayMetrics.heightPixels;

    Glide.with(this)
        .load(uri)
        .asBitmap()
        .centerCrop()
        .into(new SimpleTarget<Bitmap>(width, height) {
          @Override public void onResourceReady(Bitmap resource,
              GlideAnimation<? super Bitmap> glideAnimation) {
            if (backgroundManager != null) {
              backgroundManager.setBitmap(resource);
            }
          }
        });
  }
}
