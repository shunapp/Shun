package com.shun.app.ui.search;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.ObjectAdapter;
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
import java.util.List;
import javax.inject.Inject;

public class SearchFragment extends android.support.v17.leanback.app.SearchFragment
    implements android.support.v17.leanback.app.SearchFragment.SearchResultProvider, SearchView {
  @Inject @Nullable RefWatcher refWatcher;
  @Inject SearchPresenter presenter;

  private BackgroundManager backgroundManager;
  private DisplayMetrics displayMetrics;
  private ArrayObjectAdapter rowsAdapter;

  @Override public void onAttach(Context context) {
    super.onAttach(context);

    AppComponent appComponent = App.getAppComponent(getActivity());

    if (appComponent != null) {
      DaggerSearchComponent.builder()
          .appComponent(appComponent)
          .searchModule(new SearchModule(this))
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

    setSearchResultProvider(this);

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

  @Override public ObjectAdapter getResultsAdapter() {
    return rowsAdapter;
  }

  @Override public boolean onQueryTextChange(String newQuery) {
    presenter.setSearchQuery(newQuery);
    return true;
  }

  @Override public boolean onQueryTextSubmit(String query) {
    presenter.setSearchQuery(query);
    return true;
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

  @Override public void clearResults() {
    if (rowsAdapter != null) {
      rowsAdapter.clear();
    }
  }

  @Override public void setMovieResults(@NonNull List<MovieViewModel> results) {
    ArrayObjectAdapter rowAdapter = new ArrayObjectAdapter(new MediaCardPresenter());
    Stream.of(results)
        .forEach(rowAdapter::add);

    String title = getString(R.string.search_headers_movies);
    HeaderItem header = new HeaderItem(0, title);
    rowsAdapter.add(new ListRow(header, rowAdapter));
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
