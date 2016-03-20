package com.shun.app.ui.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import com.shun.app.App;
import com.shun.app.AppComponent;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;

public abstract class BaseActivity extends Activity {
  @Inject protected EventBus eventBus;

  protected boolean shouldRegisterEventBus() {
    return false;
  }

  @Override @CallSuper protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    AppComponent appComponent = App.getAppComponent(this);

    if (appComponent != null) {
      setupComponent(appComponent);
    }
  }

  @Override protected void onStart() {
    super.onStart();

    if (shouldRegisterEventBus() && eventBus != null) {
      eventBus.register(this);
    }
  }

  @Override protected void onStop() {
    if (shouldRegisterEventBus() && eventBus != null) {
      eventBus.unregister(this);
    }

    super.onStop();
  }

  protected void setupComponent(@NonNull AppComponent appComponent) {
    appComponent.inject(this);
  }
}
