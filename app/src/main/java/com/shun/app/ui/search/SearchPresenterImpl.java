package com.shun.app.ui.search;

import android.net.Uri;
import android.support.annotation.NonNull;
import com.shun.app.domain.catalog.CatalogManager;
import com.shun.app.domain.models.MediaItem;
import com.shun.app.ui.common.BaseFragmentPresenter;
import com.shun.app.ui.events.ShowMediaItemDetails;
import com.shun.app.ui.viewmodels.MediaItemViewModel;
import com.shun.app.ui.viewmodels.MovieViewModel;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.greenrobot.eventbus.EventBus;

public class SearchPresenterImpl extends BaseFragmentPresenter<SearchView>
    implements SearchPresenter {
  private static final int BACKGROUND_UPDATE_DELAY = 300;
  private static final int SEARCH_DELAY = 500;

  private final CatalogManager catalogManager;
  private String query;
  private Timer searchTimer;
  private Uri backgroundUri;
  private Timer backgroundTimer;

  public SearchPresenterImpl(@NonNull SearchView view, EventBus eventBus,
      CatalogManager catalogManager) {
    super(view, eventBus);
    this.catalogManager = catalogManager;
  }

  private void startSearchTimer() {
    if (searchTimer != null) {
      cancelSearchTimer();
    }

    searchTimer = new Timer();
    searchTimer.schedule(new SearchTask(), SEARCH_DELAY);
  }

  private void cancelSearchTimer() {
    searchTimer.cancel();
    searchTimer = null;
  }

  private void startBackgroundTimer() {
    if (backgroundTimer != null) {
      cancelBackgroundTimer();
    }

    backgroundTimer = new Timer();
    backgroundTimer.schedule(new UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY);
  }

  private void cancelBackgroundTimer() {
    backgroundTimer.cancel();
    backgroundTimer = null;
  }

  @Override public void setSearchQuery(String query) {
    this.query = query;
    startSearchTimer();
  }

  @Override public void selectItem(MediaItemViewModel item) {
    if (item != null) {
      backgroundUri = item.getBackdropUri();
      startBackgroundTimer();
    }
  }

  @Override public void clickItem(MediaItemViewModel item) {
    if (item != null) {
      MediaItem mediaItem = item.getMediaItem();
      eventBus.post(new ShowMediaItemDetails(mediaItem));
    }
  }

  private class SearchTask extends TimerTask {
    @Override public void run() {
      SearchView view = getView();

      if (view == null) {
        return;
      }

      runOnView(() -> view.clearResults());

      catalogManager.searchMovies(query)
          .subscribe(results -> {

            if (view != null) {
              List<MovieViewModel> viewModels = MovieViewModel.fromList(results);
              runOnView(() -> view.setMovieResults(viewModels));
            }

            cancelSearchTimer();
          });
    }
  }

  private class UpdateBackgroundTask extends TimerTask {
    @Override public void run() {
      runOnView(() -> {
        SearchView view = getView();

        if (view != null && backgroundUri != null) {
          view.setBackgroundUri(backgroundUri);
        }

        cancelBackgroundTimer();
      });
    }
  }
}
