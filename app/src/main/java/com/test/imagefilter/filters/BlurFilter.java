package com.test.imagefilter.filters;

import android.graphics.Bitmap;

public final class BlurFilter extends AbstractFilter {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlurFilter)) return false;

        BlurFilter that = (BlurFilter) o;

        return this.getClass().getName().equals(that.getClass().getName());
    }

    @Override
    public Bitmap getFilterResult(Bitmap bitmap) {
        return cn.Ragnarok.BlurFilter.changeToAverageBlur(bitmap, 5); // maskSize
    }
}
