package com.waltoncraftsllc.waterfrontcashflow.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;

public class VerticalTextView extends androidx.appcompat.widget.AppCompatTextView {
    final boolean topDown;
    public VerticalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        final int gravity = getGravity();
        topDown = ( Gravity.isVertical(gravity)  &&  (gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.BOTTOM );
        if ( !topDown ) {
            setGravity( (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) | Gravity.TOP);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        TextPaint textPaint = getPaint();
        textPaint.setColor( getCurrentTextColor() );
        textPaint.drawableState = getDrawableState();

        canvas.save();

        if ( topDown ) {
            canvas.translate( getWidth(), 0 );
            canvas.rotate( 90 );
        } else {
            canvas.translate( 0, getHeight() );
            canvas.rotate( -90 );
        }

        canvas.translate(getCompoundPaddingLeft(), getExtendedPaddingTop());
        getLayout().draw(canvas);

        Paint paint = getPaint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(0xFF0000);
        canvas.drawRect(10, 10, 100, 100, paint);

        canvas.restore();
    }
}
