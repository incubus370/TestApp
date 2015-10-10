package com.test.imagefilter.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.test.imagefilter.R;

/**
 * Image view extended to show vertical divider in center of canvas
 */
public class ImageViewWithVerticalDivider extends ImageView {

    private int dividerColor;
    private Paint dividerPaint;
    private float dividerWidth;
    private boolean showDivider;

    public ImageViewWithVerticalDivider(Context context) {
        this(context, null);
    }

    public ImageViewWithVerticalDivider(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageViewWithVerticalDivider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a =
            context.obtainStyledAttributes(attrs, R.styleable.ImageViewWithVerticalDivider,
                defStyleAttr, 0);
        dividerPaint = new Paint();
        dividerPaint.setAntiAlias(true);
        dividerWidth = a.getDimension(R.styleable.ImageViewWithVerticalDivider_dividerWidth, 0);
        dividerColor =
            a.getColor(R.styleable.ImageViewWithVerticalDivider_dividerColor, Color.TRANSPARENT);
        showDivider = a.getBoolean(R.styleable.ImageViewWithVerticalDivider_showDivider, false);
        configureDividerPaint();
        a.recycle();
    }

    public void setDivider(float dividerWidth, int dividerColor) {
        this.dividerWidth = dividerWidth;
        this.dividerColor = dividerColor;
        configureDividerPaint();
        if (showDivider) {
            invalidate();
        }
    }

    public void setShowDivider(boolean showDivider) {
        if (this.showDivider ^ showDivider) {
            this.showDivider = showDivider;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showDivider) {
            float height = getHeight();
            float halfWidth = getWidth() / 2;
            canvas.drawLine(halfWidth, 0, halfWidth, height, dividerPaint);
        }
    }

    private void configureDividerPaint() {
        dividerPaint.setColor(dividerColor);
        dividerPaint.setStrokeWidth(dividerWidth);
    }
}
