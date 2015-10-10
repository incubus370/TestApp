package com.test.imagefilter.filters;

import android.graphics.Bitmap;

public final class PixelateFilter extends AbstractFilter {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PixelateFilter)) return false;

        PixelateFilter that = (PixelateFilter) o;

        return this.getClass().getName().equals(that.getClass().getName());
    }

    @Override
    public Bitmap getFilterResult(Bitmap bitmap) {
        return cn.Ragnarok.PixelateFilter.changeToPixelate(bitmap, 10);
    }
}
