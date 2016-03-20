package com.shun.app.ui.search;

import com.shun.app.ui.common.FragmentPresenter;
import com.shun.app.ui.viewmodels.MediaItemViewModel;

public interface SearchPresenter extends FragmentPresenter {
  void setSearchQuery(String query);

  void selectItem(MediaItemViewModel item);

  void clickItem(MediaItemViewModel item);
}
