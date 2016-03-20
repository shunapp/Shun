package com.shun.app;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.Nullable;
import com.shun.app.domain.DaggerDomainComponent;
import com.shun.app.domain.DomainComponent;
import com.shun.app.domain.catalog.CatalogModule;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class App extends Application {
  private AppComponent appComponent;
  private RefWatcher refWatcher;

  @Override public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      refWatcher = LeakCanary.install(this);
    }

    DomainComponent domainComponent = DaggerDomainComponent.builder()
        .catalogModule(new CatalogModule())
        .build();

    appComponent = DaggerAppComponent.builder()
        .domainComponent(domainComponent)
        .appModule(new AppModule(this))
        .build();

    appComponent.inject(this);
  }

  public AppComponent getAppComponent() {
    return appComponent;
  }

  @Nullable public RefWatcher getRefWatcher() {
    return refWatcher;
  }

  @Nullable public static AppComponent getAppComponent(Activity activity) {
    if (activity == null) {
      return null;
    }

    Application application = activity.getApplication();

    if (application instanceof App) {
      App app = (App) application;
      return app.getAppComponent();
    }

    return null;
  }
}
