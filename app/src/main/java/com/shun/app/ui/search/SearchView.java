package com.shun.app.ui.search;

import android.net.Uri;
import android.support.annotation.NonNull;
import com.shun.app.ui.viewmodels.MovieViewModel;
import java.util.List;

public interface SearchView {
  void clearResults();

  void setMovieResults(@NonNull List<MovieViewModel> results);

  void setBackgroundUri(Uri uri);
}
