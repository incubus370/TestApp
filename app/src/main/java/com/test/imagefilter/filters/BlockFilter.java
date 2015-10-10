package com.test.imagefilter.filters;

import android.graphics.Bitmap;

public final class BlockFilter extends AbstractFilter {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlockFilter)) return false;

        BlockFilter that = (BlockFilter) o;

        return this.getClass().getName().equals(that.getClass().getName());
    }

    @Override
    public Bitmap getFilterResult(Bitmap bitmap) {
        return cn.Ragnarok.BlockFilter.changeToBrick(bitmap);
    }
}
