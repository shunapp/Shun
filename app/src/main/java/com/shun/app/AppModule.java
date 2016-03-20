package com.shun.app;

import com.squareup.leakcanary.RefWatcher;
import dagger.Module;
import dagger.Provides;
import org.greenrobot.eventbus.EventBus;

@Module
public class AppModule {
  private App app;

  public AppModule(App app) {
    this.app = app;
  }

  @Provides @AppScope RefWatcher provideRefWatcher() {
    return app.getRefWatcher();
  }

  @Provides @AppScope EventBus provideEventBus() {
    return EventBus.getDefault();
  }
}
