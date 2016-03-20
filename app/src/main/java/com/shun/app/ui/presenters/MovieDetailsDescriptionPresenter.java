package com.shun.app.ui.presenters;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;
import com.shun.app.ui.viewmodels.MovieViewModel;

public class MovieDetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {
  @Override protected void onBindDescription(ViewHolder viewHolder, Object itemData) {
    if (!(itemData instanceof MovieViewModel)) {
      return;
    }

    MovieViewModel viewModel = (MovieViewModel) itemData;
    viewHolder.getTitle()
        .setText(viewModel.getTitle());
    viewHolder.getSubtitle()
        .setText(String.valueOf(viewModel.getReleaseYear()));
    viewHolder.getBody()
        .setText(viewModel.getOverview());
  }
}
