package com.test.imagefilter.filters;

import android.graphics.Bitmap;

public final class SharpenFilter extends AbstractFilter {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SharpenFilter)) return false;

        SharpenFilter that = (SharpenFilter) o;

        return this.getClass().getName().equals(that.getClass().getName());
    }

    @Override
    public Bitmap getFilterResult(Bitmap bitmap) {
        return cn.Ragnarok.SharpenFilter.changeToSharpen(bitmap);
    }
}
