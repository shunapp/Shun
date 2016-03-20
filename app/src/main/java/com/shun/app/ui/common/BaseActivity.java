package com.shun.app.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import com.shun.app.App;
import com.shun.app.AppComponent;
import com.shun.app.domain.models.MediaItem;
import com.shun.app.ui.details.DetailsActivity;
import com.shun.app.ui.events.ShowMediaItemDetails;
import com.shun.app.ui.events.ShowSearch;
import com.shun.app.ui.search.SearchActivity;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public abstract class BaseActivity extends Activity {
  @Inject protected EventBus eventBus;

  @Override @CallSuper protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    AppComponent appComponent = App.getAppComponent(this);

    if (appComponent != null) {
      setupComponent(appComponent);
    }
  }

  @Override protected void onStart() {
    super.onStart();

    if (eventBus != null) {
      eventBus.register(this);
    }
  }

  @Override protected void onStop() {
    if (eventBus != null) {
      eventBus.unregister(this);
    }

    super.onStop();
  }

  @Subscribe public void onShowMediaItemDetails(ShowMediaItemDetails event) {
    MediaItem mediaItem = event.getMediaItem();

    if (mediaItem != null) {
      Intent intent = DetailsActivity.createIntent(this, mediaItem);
      startActivity(intent);
    }
  }

  @Subscribe public void onShowSearch(ShowSearch event) {
    Intent intent = SearchActivity.createIntent(this);
    startActivity(intent);
  }

  protected void setupComponent(@NonNull AppComponent appComponent) {
    appComponent.inject(this);
  }
}
