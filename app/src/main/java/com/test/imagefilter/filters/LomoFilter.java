package com.test.imagefilter.filters;

import android.graphics.Bitmap;

public final class LomoFilter extends AbstractFilter {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LomoFilter)) return false;

        LomoFilter that = (LomoFilter) o;

        return this.getClass().getName().equals(that.getClass().getName());
    }

    @Override
    public Bitmap getFilterResult(Bitmap bitmap) {
        double radius = (bitmap.getWidth() / 2) * 95 / 100;
        return cn.Ragnarok.LomoFilter.changeToLomo(bitmap, radius);
    }
}
