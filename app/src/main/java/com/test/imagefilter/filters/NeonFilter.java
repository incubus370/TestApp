package com.test.imagefilter.filters;

import android.graphics.Bitmap;

public final class NeonFilter extends AbstractFilter {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NeonFilter)) return false;

        NeonFilter that = (NeonFilter) o;

        return this.getClass().getName().equals(that.getClass().getName());
    }

    @Override
    public Bitmap getFilterResult(Bitmap bitmap) {
        return cn.Ragnarok.NeonFilter.changeToNeon(bitmap, 200, 50, 100);
    }
}