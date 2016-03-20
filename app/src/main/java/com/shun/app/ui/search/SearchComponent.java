package com.shun.app.ui.search;

import com.shun.app.AppComponent;
import com.shun.app.ui.ActivityScope;
import dagger.Component;

@ActivityScope
@Component(
    dependencies = {
        AppComponent.class
    },
    modules = SearchModule.class)
public interface SearchComponent {
  void inject(SearchFragment fragment);

  SearchPresenter getSearchPresenter();
}
