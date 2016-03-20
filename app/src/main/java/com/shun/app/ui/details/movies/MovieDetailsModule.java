package com.shun.app.ui.details.movies;

import android.support.annotation.NonNull;
import com.shun.app.domain.catalog.CatalogManager;
import dagger.Module;
import dagger.Provides;
import java.lang.ref.WeakReference;
import org.greenrobot.eventbus.EventBus;

@Module
public class MovieDetailsModule {
  @NonNull private WeakReference<MovieDetailsView> viewRef;

  public MovieDetailsModule(MovieDetailsView view) {
    this.viewRef = new WeakReference<>(view);
  }

  @Provides public MovieDetailsView provideView() {
    return viewRef.get();
  }

  @Provides public MovieDetailsPresenter providePresenter(MovieDetailsView view, EventBus eventBus,
      CatalogManager catalogManager) {
    return new MovieDetailsPresenterImpl(view, eventBus, catalogManager);
  }
}
