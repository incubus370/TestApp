package com.test.imagefilter.filters;

import android.graphics.Bitmap;

public final class ReliefFilter extends AbstractFilter {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReliefFilter)) return false;

        ReliefFilter that = (ReliefFilter) o;

        return this.getClass().getName().equals(that.getClass().getName());
    }

    @Override
    public Bitmap getFilterResult(Bitmap bitmap) {
        return cn.Ragnarok.ReliefFilter.changeToRelief(bitmap);
    }
}

