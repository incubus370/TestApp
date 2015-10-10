package com.test.imagefilter.filters;

import android.graphics.Bitmap;

public final class MotionBlurFilter extends AbstractFilter {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MotionBlurFilter)) return false;

        MotionBlurFilter that = (MotionBlurFilter) o;

        return this.getClass().getName().equals(that.getClass().getName());
    }

    @Override
    public Bitmap getFilterResult(Bitmap bitmap) {
        return cn.Ragnarok.MotionBlurFilter.changeToMotionBlur(bitmap, 5, 1);
    }
}