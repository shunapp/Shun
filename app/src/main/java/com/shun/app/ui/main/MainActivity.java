package com.shun.app.ui.main;

import android.content.Intent;
import android.os.Bundle;
import com.shun.app.R;
import com.shun.app.domain.models.MediaItem;
import com.shun.app.ui.common.BaseActivity;
import com.shun.app.ui.details.DetailsActivity;
import com.shun.app.ui.events.ShowMediaItemDetails;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends BaseActivity {
  @Override protected boolean shouldRegisterEventBus() {
    return true;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Subscribe public void onShowMediaItemDetails(ShowMediaItemDetails details) {
    MediaItem mediaItem = details.getMediaItem();

    if (mediaItem != null) {
      Intent intent = DetailsActivity.createIntent(this, mediaItem);
      startActivity(intent);
    }
  }
}
