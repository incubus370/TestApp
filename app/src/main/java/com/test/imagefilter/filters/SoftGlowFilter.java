package com.test.imagefilter.filters;

import android.graphics.Bitmap;

public final class SoftGlowFilter extends AbstractFilter {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SoftGlowFilter)) return false;

        SoftGlowFilter that = (SoftGlowFilter) o;

        return this.getClass().getName().equals(that.getClass().getName());
    }

    @Override
    public Bitmap getFilterResult(Bitmap bitmap) {
        return cn.Ragnarok.SoftGlowFilter.softGlowFilter(bitmap, 0.6);
    }
}