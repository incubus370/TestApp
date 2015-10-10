package com.test.imagefilter.ui.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.test.imagefilter.filters.Filter;

/**
 * Glide transformation to filter image and merge source and filtered image into one
 */
public class PreviewTransformation extends BitmapTransformation {

    private Filter filter;

    public PreviewTransformation(Context context, Filter filter) {
        super(context);
        this.filter = filter;
    }

    @Override
    public String getId() {
        return "source" + filter.getClass().getName();
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap result =
            pool.get(toTransform.getWidth(), toTransform.getHeight(), toTransform.getConfig());
        if (result == null) {
            // Use ARGB_8888 since we're going to add alpha to the image.
            result = Bitmap.createBitmap(toTransform.getWidth(), toTransform.getHeight(),
                toTransform.getConfig());
        }
        Bitmap filtered = filter.getFilterResult(toTransform);
        Canvas c = new Canvas(result);
        Rect drawRect = new Rect(0, 0, c.getWidth() / 2, c.getHeight());
        c.drawBitmap(toTransform, drawRect, drawRect, null);
        drawRect = new Rect(c.getWidth() / 2, 0, c.getWidth(), c.getHeight());
        c.drawBitmap(filtered, drawRect, drawRect, null);
        return result;
    }
}
