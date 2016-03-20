package com.shun.app.ui.main;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.DisplayMetrics;
import com.annimon.stream.Stream;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.shun.app.App;
import com.shun.app.AppComponent;
import com.shun.app.R;
import com.shun.app.ui.presenters.MediaCardPresenter;
import com.shun.app.ui.viewmodels.MediaItemViewModel;
import com.shun.app.ui.viewmodels.MovieViewModel;
import com.squareup.leakcanary.RefWatcher;
import java.util.Collection;
import javax.inject.Inject;

public class MainFragment extends BrowseFragment implements MainView {
  @Inject @Nullable RefWatcher refWatcher;
  @Inject MainPresenter presenter;

  private BackgroundManager backgroundManager;
  private DisplayMetrics displayMetrics;
  private ArrayObjectAdapter rowsAdapter;

  @Override public void onAttach(Context context) {
    super.onAttach(context);

    AppComponent appComponent = App.getAppComponent(getActivity());

    if (appComponent != null) {
      DaggerMainComponent.builder()
          .appComponent(appComponent)
          .mainModule(new MainModule(this))
          .build()
          .inject(this);
    }

    rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

    presenter.initialize();
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    Activity activity = getActivity();
    backgroundManager = BackgroundManager.getInstance(activity);
    backgroundManager.attach(activity.getWindow());

    displayMetrics = new DisplayMetrics();
    getActivity().getWindowManager()
        .getDefaultDisplay()
        .getMetrics(displayMetrics);
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setAdapter(rowsAdapter);

    setOnSearchClickedListener(view -> presenter.search());
    setOnItemViewSelectedListener(this::onItemSelected);
    setOnItemViewClickedListener(this::onItemClicked);

    presenter.onCreated();
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

  @Override public void setPopularMovies(Collection<MovieViewModel> movies) {
    MediaCardPresenter cardPresenter = new MediaCardPresenter();
    ArrayObjectAdapter rowAdapter = new ArrayObjectAdapter(cardPresenter);

    Stream.of(movies)
        .forEach(movie -> rowAdapter.add(movie));

    String name = getString(R.string.main_header_popular);
    HeaderItem header = new HeaderItem(name);
    rowsAdapter.add(new ListRow(header, rowAdapter));
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

  public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
      RowPresenter.ViewHolder rowViewHolder, Row row) {
    if (item instanceof MediaItemViewModel) {
      MediaItemViewModel viewModel = (MediaItemViewModel) item;
      presenter.selectItem(viewModel);
    }
  }

  public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
      RowPresenter.ViewHolder rowViewHolder, Row row) {
    if (item instanceof MediaItemViewModel) {
      MediaItemViewModel viewModel = (MediaItemViewModel) item;
      presenter.clickItem(viewModel);
    }
  }
}
