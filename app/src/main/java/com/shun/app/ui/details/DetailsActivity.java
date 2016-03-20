package com.shun.app.ui.details;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.shun.app.R;
import com.shun.app.domain.models.MediaItem;
import com.shun.app.domain.models.Movie;
import com.shun.app.ui.common.BaseActivity;
import com.shun.app.ui.details.movies.MovieDetailsFragment;

public class DetailsActivity extends BaseActivity {
  private final static String EXTRA_MEDIA_ITEM = "mediaItem";

  public static Intent createIntent(Context context, MediaItem mediaItem) {
    Intent intent = new Intent(context, DetailsActivity.class);
    intent.putExtra(EXTRA_MEDIA_ITEM, mediaItem);

    return intent;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_details);

    if (savedInstanceState == null) {
      Intent intent = getIntent();
      MediaItem mediaItem = intent.getParcelableExtra(EXTRA_MEDIA_ITEM);

      Fragment fragment = null;

      if (mediaItem instanceof Movie) {
        Movie movie = (Movie) mediaItem;
        fragment = MovieDetailsFragment.newInstance(movie);
      }

      if (fragment == null) {
        finish();
        return;
      }

      getFragmentManager().beginTransaction()
          .replace(R.id.details_frame, fragment)
          .commit();
    }
  }
}
