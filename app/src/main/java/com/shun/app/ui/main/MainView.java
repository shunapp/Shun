package com.shun.app.ui.main;

import android.net.Uri;
import com.shun.app.ui.viewmodels.MovieViewModel;
import java.util.Collection;

public interface MainView {
  void setPopularMovies(Collection<MovieViewModel> movies);

  void setBackgroundUri(Uri uri);
}
