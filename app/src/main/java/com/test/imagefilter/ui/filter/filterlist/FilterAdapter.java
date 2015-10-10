package com.test.imagefilter.ui.filter.filterlist;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.test.imagefilter.filters.Filter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Filter adapter for recycle view to show filter list with preview
 */
public class FilterAdapter extends RecyclerView.Adapter<FilterViewHolder> {

    private List<Filter> filterList;
    private String imageCacheUUID;
    private String pathToPreviewImage;
    private ViewHolderProvider<FilterViewHolder> viewHolderProvider;

    public FilterAdapter(ViewHolderProvider<FilterViewHolder> viewHolderProvider) {
        this.viewHolderProvider = viewHolderProvider;
        this.filterList = new ArrayList();
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    @Override
    public void onBindViewHolder(FilterViewHolder holder, int position) {
        holder.bind(pathToPreviewImage, filterList.get(position), imageCacheUUID);
    }

    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewHolderProvider.getViewHolderInstance(parent);
    }

    void setData(String pathToPreviewImage, List<Filter> filterList) {
        this.imageCacheUUID = UUID.randomUUID().toString();
        this.pathToPreviewImage = pathToPreviewImage;
        this.filterList.clear();
        this.filterList.addAll(filterList);
        notifyDataSetChanged();
    }
}
