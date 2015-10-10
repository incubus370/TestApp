package com.test.imagefilter.filters;

import android.graphics.Bitmap;

public final class HDRFilter extends AbstractFilter {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HDRFilter)) return false;

        HDRFilter that = (HDRFilter) o;

        return this.getClass().getName().equals(that.getClass().getName());
    }

    @Override
    public Bitmap getFilterResult(Bitmap bitmap) {
        return cn.Ragnarok.HDRFilter.changeToHDR(bitmap);
    }
}