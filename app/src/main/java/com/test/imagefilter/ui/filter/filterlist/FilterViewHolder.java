package com.test.imagefilter.ui.filter.filterlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.signature.StringSignature;
import com.test.imagefilter.R;
import com.test.imagefilter.filters.Filter;

/**
 * Filter adapter view holder with filter preview
 */
public class FilterViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.filter_name)
    TextView filterName;
    @Bind(R.id.filter_preview_image)
    ImageView imageView;
    private Filter filter;
    private SelectFilterFragment.FilterSelectionListener filterSelectionListener;
    private RequestManager requestManager;

    public FilterViewHolder(Context context,
        SelectFilterFragment.FilterSelectionListener filterSelectionListener) {
        super(LayoutInflater.from(context).inflate(R.layout.adapter_filter, null));
        this.requestManager = Glide.with(context);
        this.filterSelectionListener = filterSelectionListener;
        ButterKnife.bind(this, itemView);
    }

    public void bind(String pathToFilterPreview, Filter filter, String imageCacheSignature) {
        this.filter = filter;
        requestManager.load(pathToFilterPreview)
            .asBitmap()
            .signature(new StringSignature(imageCacheSignature))
            .centerCrop()
            .transform(new FilterTransformation(itemView.getContext(), filter))
            .into(imageView);
        filterName.setText(filter.filterCodeName());
    }

    @OnClick(R.id.filter_preview_root_layout)
    void OnFilterClick() {
        filterSelectionListener.onFilterSelected(filter);
    }

    /**
     * Filter view holder instance provider for adapter
     */
    public static class ViewHolderProvider
        implements com.test.imagefilter.ui.filter.filterlist.ViewHolderProvider<FilterViewHolder> {

        private Context context;
        private SelectFilterFragment.FilterSelectionListener filterSelectionListener;

        public ViewHolderProvider(Context context,
            SelectFilterFragment.FilterSelectionListener filterSelectionListener) {
            this.context = context;
            this.filterSelectionListener = filterSelectionListener;
            if (filterSelectionListener == null) {
                throw new IllegalArgumentException("filterSelectionListener can not be null");
            }
        }

        @Override
        public FilterViewHolder getViewHolderInstance(ViewGroup parent) {
            return new FilterViewHolder(context, filterSelectionListener);
        }
    }
}
