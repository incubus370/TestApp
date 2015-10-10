package com.test.imagefilter.filters;

import android.graphics.Bitmap;

public final class InvertFilter extends AbstractFilter {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvertFilter)) return false;

        InvertFilter that = (InvertFilter) o;

        return this.getClass().getName().equals(that.getClass().getName());
    }

    @Override
    public Bitmap getFilterResult(Bitmap bitmap) {
        return cn.Ragnarok.InvertFilter.chageToInvert(bitmap);
    }
}