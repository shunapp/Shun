package com.shun.app.ui.main;

import com.shun.app.ui.common.FragmentPresenter;
import com.shun.app.ui.viewmodels.MediaItemViewModel;

public interface MainPresenter extends FragmentPresenter {
  void selectItem(MediaItemViewModel item);

  void clickItem(MediaItemViewModel item);
}
