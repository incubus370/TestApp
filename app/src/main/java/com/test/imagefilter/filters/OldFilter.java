package com.test.imagefilter.filters;

import android.graphics.Bitmap;

public final class OldFilter extends AbstractFilter {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OldFilter)) return false;

        OldFilter that = (OldFilter) o;

        return this.getClass().getName().equals(that.getClass().getName());
    }

    @Override
    public Bitmap getFilterResult(Bitmap bitmap) {
        return cn.Ragnarok.OldFilter.changeToOld(bitmap);
    }
}