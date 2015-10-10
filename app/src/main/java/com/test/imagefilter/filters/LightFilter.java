package com.test.imagefilter.filters;

import android.graphics.Bitmap;

public final class LightFilter extends AbstractFilter {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LightFilter)) return false;

        LightFilter that = (LightFilter) o;

        return this.getClass().getName().equals(that.getClass().getName());
    }

    @Override
    public Bitmap getFilterResult(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        return cn.Ragnarok.LightFilter.changeToLight(bitmap, width / 2, height / 2,
            Math.min(width / 2, height / 2)); // centerX, centerY, radius
    }
}
