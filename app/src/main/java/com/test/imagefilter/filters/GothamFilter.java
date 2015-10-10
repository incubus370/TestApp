package com.test.imagefilter.filters;

import android.graphics.Bitmap;

public final class GothamFilter extends AbstractFilter {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GothamFilter)) return false;

        GothamFilter that = (GothamFilter) o;

        return this.getClass().getName().equals(that.getClass().getName());
    }

    @Override
    public Bitmap getFilterResult(Bitmap bitmap) {
        return cn.Ragnarok.GothamFilter.changeToGotham(bitmap);
    }
}