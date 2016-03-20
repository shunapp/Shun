package com.shun.app.ui.viewmodels;

import android.support.annotation.NonNull;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.shun.app.domain.models.Movie;
import java.util.List;

public class MovieViewModel extends MediaItemViewModel {
  public MovieViewModel(@NonNull Movie movie) {
    super(movie);
  }

  public static List<MovieViewModel> fromList(@NonNull List<Movie> items) {
    return Stream.of(items)
        .map(MovieViewModel::new)
        .collect(Collectors.toList());
  }
}
