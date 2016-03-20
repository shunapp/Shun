package com.shun.app.ui.common;

public interface FragmentPresenter extends Presenter {
  void initialize();

  void onCreated();

  void onStop();

  void onDestroy();
}
