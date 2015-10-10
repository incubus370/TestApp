package com.test.imagefilter.filters;

import android.graphics.Bitmap;

public final class GrayFilter extends AbstractFilter {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GrayFilter)) return false;

        GrayFilter that = (GrayFilter) o;

        return this.getClass().getName().equals(that.getClass().getName());
    }

    @Override
    public Bitmap getFilterResult(Bitmap bitmap) {
        return cn.Ragnarok.GrayFilter.changeToGray(bitmap);
    }
}
