package com.test.imagefilter.filters;

import android.graphics.Bitmap;

public final class TvFilter extends AbstractFilter {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TvFilter)) return false;

        TvFilter that = (TvFilter) o;

        return this.getClass().getName().equals(that.getClass().getName());
    }

    @Override
    public Bitmap getFilterResult(Bitmap bitmap) {
        return cn.Ragnarok.TvFilter.changeToTV(bitmap);
    }
}
