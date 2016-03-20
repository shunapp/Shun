package com.shun.app.ui.presenters;

import android.content.res.Resources;
import android.net.Uri;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.shun.app.R;
import com.shun.app.ui.viewmodels.MediaItemViewModel;

public class MediaCardPresenter extends Presenter {
  @Override public ViewHolder onCreateViewHolder(ViewGroup parent) {
    ImageCardView cardView = new ImageCardView(parent.getContext());
    cardView.setFocusable(true);
    cardView.setFocusableInTouchMode(true);
    return new ViewHolder(cardView);
  }

  @Override public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
    MediaItemViewModel viewModel = (MediaItemViewModel) item;

    ImageCardView cardView = (ImageCardView) viewHolder.view;
    cardView.setTitleText(viewModel.getTitle());
    cardView.setContentText(viewModel.getOverview());

    Uri posterUrl = viewModel.getPosterUri();

    if (posterUrl != null) {
      Resources res = cardView.getResources();
      int width = res.getDimensionPixelSize(R.dimen.media_card_main_image_width);
      int height = res.getDimensionPixelSize(R.dimen.media_card_main_image_height);
      cardView.setMainImageDimensions(width, height);

      Glide.with(cardView.getContext())
          .load(posterUrl)
          .into(cardView.getMainImageView());
    }
  }

  @Override public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
    ImageCardView cardView = (ImageCardView) viewHolder.view;
    cardView.setBadgeImage(null);
    cardView.setMainImage(null);
  }
}
