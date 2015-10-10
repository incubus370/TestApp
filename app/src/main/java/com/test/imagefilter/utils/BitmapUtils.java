package com.test.imagefilter.utils;

import android.graphics.BitmapFactory;
import java.io.File;

/**
 * Utilities class for working with image
 */
public final class BitmapUtils {

    private BitmapUtils() {
    }

    /**
     * Method to check that file can be processed as image
     *
     * @param file The File to be check
     * @return true if image can be loaded and processed, false otherwise
     */

    public static boolean isValidImageFile(File file) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        return (options.outHeight > 0 && options.outWidth > 0);
    }
}
