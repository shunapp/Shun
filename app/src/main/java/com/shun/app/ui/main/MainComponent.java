package com.shun.app.ui.main;

import com.shun.app.AppComponent;
import com.shun.app.ui.ActivityScope;
import dagger.Component;

@ActivityScope
@Component(
    dependencies = {
        AppComponent.class
    },
    modules = MainModule.class)
public interface MainComponent {
  void inject(MainFragment fragment);

  MainPresenter getMainPresenter();
}
