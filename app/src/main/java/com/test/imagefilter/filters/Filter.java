package com.test.imagefilter.filters;

import android.graphics.Bitmap;

/**
 * Main image filter interface
 */
public interface Filter {

    /**
     * Return filter instance codename
     */
    String filterCodeName();

    /**
     * Method to apply filter instance to specified bitmap
     *
     * @param bitmap source bitmap for filter
     * @return filtered bitmap
     */
    Bitmap getFilterResult(Bitmap bitmap);
}
