package com.test.imagefilter.filters;

import android.graphics.Bitmap;

public final class OilFilter extends AbstractFilter {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OilFilter)) return false;

        OilFilter that = (OilFilter) o;

        return this.getClass().getName().equals(that.getClass().getName());
    }

    @Override
    public Bitmap getFilterResult(Bitmap bitmap) {
        return cn.Ragnarok.OilFilter.changeToOil(bitmap, 5);
    }
}