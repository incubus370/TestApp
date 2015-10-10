package com.test.imagefilter.filters;

import android.graphics.Bitmap;

public final class GaussianBlurFilter extends AbstractFilter {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GaussianBlurFilter)) return false;

        GaussianBlurFilter that = (GaussianBlurFilter) o;

        return this.getClass().getName().equals(that.getClass().getName());
    }

    @Override
    public Bitmap getFilterResult(Bitmap bitmap) {
        return cn.Ragnarok.GaussianBlurFilter.changeToGaussianBlur(bitmap, 1.2); // sigma
    }
}