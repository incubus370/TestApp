package com.test.imagefilter.ui.filter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.signature.StringSignature;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.test.imagefilter.R;
import com.test.imagefilter.filters.Filter;
import com.test.imagefilter.ui.BaseFragment;
import com.test.imagefilter.ui.IImageFilterActivity;
import com.test.imagefilter.ui.custom.ImageViewWithVerticalDivider;
import com.test.imagefilter.ui.filter.filterlist.SelectFilterFragment;
import com.test.imagefilter.ui.filter.filterlist.SelectFilterFragmentBuilder;
import com.test.imagefilter.utils.FragmentUtils;
import java.util.UUID;

/**
 * Fragment to show loaded image and apply filter to it
 */
public class ImageFilterFragment extends BaseFragment
    implements SelectFilterFragment.FilterSelectionListener {

    private static final String SELECT_FILTER_FRAGMENT_TAG = "select_filter";
    @Bind(R.id.filter_progress_bar)
    ProgressBar filterProgressBar;
    @Bind(R.id.filter_image)
    ImageViewWithVerticalDivider imageView;
    @Arg
    String localFilePath;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private BitmapImageViewTarget bitmapImageViewTarget;
    private Filter currentFilter;
    private String imageCacheUUID;
    private IImageFilterActivity pickImageActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        pickImageActivity = FragmentUtils.getParent(this, IImageFilterActivity.class);
        if (pickImageActivity == null) {
            throw new IllegalArgumentException(
                "At least one fragment parent should implement IImageFilterActivity");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        //generate UUID to make valid Glide cache only for current session
        imageCacheUUID = UUID.randomUUID().toString();
        View root = inflater.inflate(R.layout.fragment_image_filter, null);
        ButterKnife.bind(this, root);
        toolbar.setTitle(R.string.app_name);
        bitmapImageViewTarget = new BitmapImageViewTarget(imageView) {
            @Override
            public Drawable getCurrentDrawable() {
                return super.getCurrentDrawable();
            }

            @Override
            public void onLoadCleared(Drawable placeholder) {
                //ignore placeholder
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                setProgressVisibility(false);
                //ignore placeholder
                showMessage(getString(R.string.error_unable_to_load_image_file));
            }

            @Override
            public void onLoadStarted(Drawable placeholder) {
                setProgressVisibility(true);
            }

            @Override
            public void onResourceReady(Bitmap resource,
                GlideAnimation<? super Bitmap> glideAnimation) {
                super.onResourceReady(resource, glideAnimation);
            }

            @Override
            protected void setResource(Bitmap resource) {
                if (currentFilter == null) {
                    imageView.setShowDivider(false);
                } else {
                    imageView.setShowDivider(true);
                }
                setProgressVisibility(false);
                super.setResource(resource);
            }
        };
        updateImage();
        return root;
    }

    @Override
    public void onDestroyView() {
        Glide.clear(bitmapImageViewTarget);
        super.onDestroyView();
    }

    @Override
    public void onFilterSelected(Filter filter) {
        setFilter(filter);
    }

    @OnClick(R.id.pick_image_button)
    void pickImage() {
        pickImageActivity.pickImage();
    }

    @OnClick(R.id.filter_button)
    void switchFilter() {
        boolean filterModeEnabled =
            getChildFragmentManager().findFragmentByTag(SELECT_FILTER_FRAGMENT_TAG) != null;
        if (filterModeEnabled) {
            removeImageFilter();
            setFilter(null);
        } else {
            showImageFilter(localFilePath);
        }
    }

    private void removeImageFilter() {
        Fragment fragment = getChildFragmentManager().findFragmentByTag(SELECT_FILTER_FRAGMENT_TAG);
        if (fragment == null) {
            return;
        }
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.remove(fragment).commit();
    }

    private void setFilter(Filter filter) {
        if (currentFilter != null && !currentFilter.equals(filter)
            || currentFilter == null && filter != null) {
            currentFilter = filter;
            updateImage();
        }
    }

    private void setProgressVisibility(boolean visible) {
        if (visible) {
            filterProgressBar.setVisibility(View.VISIBLE);
        } else {
            filterProgressBar.setVisibility(View.GONE);
        }
    }

    private void showImageFilter(String pathToPreviewImage) {
        Fragment fragment = new SelectFilterFragmentBuilder(pathToPreviewImage).build();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.image_filter_container, fragment,
            SELECT_FILTER_FRAGMENT_TAG).commit();
    }

    private void updateImage() {
        if (currentFilter == null) {
            Glide.with(this)
                .load(localFilePath)
                .asBitmap()
                .signature(new StringSignature(imageCacheUUID))
                .into(bitmapImageViewTarget);
        } else {
            Glide.with(this)
                .load(localFilePath)
                .asBitmap()
                .signature(new StringSignature(imageCacheUUID))
                .transform(new PreviewTransformation(imageView.getContext(), currentFilter))
                .into(bitmapImageViewTarget);
        }
    }
}
