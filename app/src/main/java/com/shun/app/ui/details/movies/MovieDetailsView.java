package com.shun.app.ui.details.movies;

import android.net.Uri;
import com.shun.app.ui.viewmodels.MovieViewModel;
import com.shun.app.ui.viewmodels.VideoViewModel;

public interface MovieDetailsView {
  void setMovie(MovieViewModel viewModel);

  void setTrailer(VideoViewModel viewModel);

  void setBackgroundUri(Uri uri);
}
