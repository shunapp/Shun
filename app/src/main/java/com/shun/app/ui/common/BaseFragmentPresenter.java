package com.shun.app.ui.common;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import org.greenrobot.eventbus.EventBus;

public abstract class BaseFragmentPresenter<V> extends BasePresenter<V>
    implements FragmentPresenter {
  public BaseFragmentPresenter(@NonNull V view, EventBus eventBus) {
    super(view, eventBus);
  }

  @Override @CallSuper public void initialize() {
  }

  @Override @CallSuper public void onCreated() {
  }

  @Override @CallSuper public void onStop() {
  }

  @Override @CallSuper public void onDestroy() {
  }
}
