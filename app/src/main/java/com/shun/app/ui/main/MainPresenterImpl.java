package com.shun.app.ui.main;

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
import rx.Subscription;

public class MainPresenterImpl extends BaseFragmentPresenter<MainView> implements MainPresenter {
  private static final int BACKGROUND_UPDATE_DELAY = 300;

  private final CatalogManager catalogManager;
  private Subscription popularMoviesSubscription;
  private Uri backgroundUri;
  private Timer backgroundTimer;

  public MainPresenterImpl(@NonNull MainView view, EventBus eventBus,
      CatalogManager catalogManager) {
    super(view, eventBus);
    this.catalogManager = catalogManager;
  }

  @Override public void initialize() {
    super.initialize();

    popularMoviesSubscription = catalogManager.popularMovies()
        .subscribe(movies -> {
          popularMoviesSubscription = null;

          MainView view = getView();

          if (view != null && movies != null) {
            List<MovieViewModel> viewModels = MovieViewModel.fromList(movies);
            view.setPopularMovies(viewModels);
          }
        });
  }

  @Override public void onStop() {
    super.onStop();

    if (backgroundTimer != null) {
      cancelBackgroundTimer();
    }

    if (popularMoviesSubscription != null) {
      popularMoviesSubscription.unsubscribe();
      popularMoviesSubscription = null;
    }
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

  private class UpdateBackgroundTask extends TimerTask {
    @Override public void run() {
      runOnView(() -> {
        MainView view = getView();

        if (view != null && backgroundUri != null) {
          view.setBackgroundUri(backgroundUri);
        }

        cancelBackgroundTimer();
      });
    }
  }
}
