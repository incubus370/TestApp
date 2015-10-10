package com.test.imagefilter.ui.filter.filterlist;

import android.content.Context;
import android.graphics.Bitmap;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.test.imagefilter.filters.Filter;

/**
 * Created by Home on 09.10.2015.
 */
public class FilterTransformation extends BitmapTransformation {

    private Filter filter;

    public FilterTransformation(Context context, Filter filter) {
        super(context);
        this.filter = filter;
    }

    @Override
    public String getId() {
        return filter.getClass().getName();
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return filter.getFilterResult(toTransform);
    }
}