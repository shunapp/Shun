package com.shun.app;

import com.shun.app.domain.DomainComponent;
import com.shun.app.domain.catalog.CatalogManager;
import com.shun.app.ui.common.BaseActivity;
import com.squareup.leakcanary.RefWatcher;
import dagger.Component;
import org.greenrobot.eventbus.EventBus;

@AppScope
@Component(
    dependencies = DomainComponent.class,
    modules = {
        AppModule.class,
    })
public interface AppComponent {
  void inject(App app);

  void inject(BaseActivity activity);

  RefWatcher getRefWatcher();

  EventBus getEventBus();

  CatalogManager getCatalogManager();
}
