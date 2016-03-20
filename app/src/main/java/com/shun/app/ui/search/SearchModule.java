package com.shun.app.ui.search;

import android.support.annotation.NonNull;
import com.shun.app.domain.catalog.CatalogManager;
import dagger.Module;
import dagger.Provides;
import java.lang.ref.WeakReference;
import org.greenrobot.eventbus.EventBus;

@Module
public class SearchModule {
  @NonNull private WeakReference<SearchView> viewRef;

  public SearchModule(SearchView view) {
    this.viewRef = new WeakReference<>(view);
  }

  @Provides public SearchView provideView() {
    return viewRef.get();
  }

  @Provides public SearchPresenter providePresenter(SearchView view, EventBus eventBus,
      CatalogManager catalogManager) {
    return new SearchPresenterImpl(view, eventBus, catalogManager);
  }
}
