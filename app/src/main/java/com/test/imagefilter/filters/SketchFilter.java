package com.test.imagefilter.filters;

import android.graphics.Bitmap;

public final class SketchFilter extends AbstractFilter {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SketchFilter)) return false;

        SketchFilter that = (SketchFilter) o;

        return this.getClass().getName().equals(that.getClass().getName());
    }

    @Override
    public Bitmap getFilterResult(Bitmap bitmap) {
        return cn.Ragnarok.SketchFilter.changeToSketch(bitmap);
    }
}
