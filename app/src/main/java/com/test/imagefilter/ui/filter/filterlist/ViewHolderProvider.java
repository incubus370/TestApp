package com.test.imagefilter.ui.filter.filterlist;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * View holder instance provider for recycle view adapter
 */
public interface ViewHolderProvider<VH extends RecyclerView.ViewHolder> {

    /**
     * Called by recycle view adapter to crete new instance of view holder
     *
     * @param parent parent of view holder view
     * @return view holder instance
     */
    VH getViewHolderInstance(ViewGroup parent);
}
