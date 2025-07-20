package com.waltoncraftsllc.waterfrontcashflow.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

import com.waltoncraftsllc.waterfrontcashflow.R;

public class DayButtonView extends View implements View.OnClickListener {
    private String mText = "120";
    private float mTextSize = 12.0F;
    private int mTextColor = 0xFFFFFF;
    private int mBackgroundColor = 0x000000;
    private OnClickListener mOnClickListener = null;

    public DayButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = null;
        try {
            ta = context.obtainStyledAttributes(attrs, R.styleable.DayButtonView, 0, 0);
            mBackgroundColor = ta.getColor(R.styleable.DayButtonView_buttonBackground, 0x000000);
            mTextColor = ta.getColor(R.styleable.DayButtonView_textColor, 0x000000);
            mText = ta.getString(R.styleable.DayButtonView_text);
            mTextSize = ta.getDimension(R.styleable.DayButtonView_textSize, 12.0F);
        } finally {
            if ( ta != null ) {
                ta.recycle();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        _paint.setTextSize(mTextSize);
        float textWidth = _paint.measureText(mText);

        int min_width = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int widget_width = resolveSizeAndState(min_width, widthMeasureSpec, 1);

        int min_height = MeasureSpec.getSize(widget_width) - (int)textWidth + getPaddingBottom() + getPaddingTop();
        int widget_height = resolveSizeAndState(min_height, heightMeasureSpec, 0);

        setMeasuredDimension(widget_width, widget_height);
    }

    @Override
    protected void onSizeChanged(int width, int height, int old_width, int old_height) {
        super.onSizeChanged(width, height, old_width, old_height);
//        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//        canvas = new Canvas(bitmap);
    }

    private Paint _paint = new Paint();
    private Rect _rect = new Rect();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        _paint.reset();
        _paint.setColor(mTextColor);
        _paint.setTextSize(24.0F);//mTextSize);
        _paint.setTypeface(Typeface.DEFAULT);
        _paint.setColor(mBackgroundColor);
        _paint.setStyle(Paint.Style.STROKE);

        canvas.save();
        canvas.getClipBounds(_rect);
        canvas.drawRGB((mBackgroundColor >> 16) & 0xFF,(mBackgroundColor >> 8) & 0xFF,mBackgroundColor & 0xFF);  // <-- this works
        canvas.drawRect(_rect, _paint); // <-- this doesn't do anything

//        Typeface typeface = paint.setTypeface(...);
        canvas.drawText((mText != null? mText: "DayButtonView"), 10, 10, _paint);
        canvas.restore();
    }

    public void setText(String text)        { mText = text; invalidate(); }
    public String getText()                 { return mText; }
    public void setTextColor(int RGB)       { mTextColor = RGB; invalidate(); }
    public int getTextColor()               { return mTextColor; }
    public void setBackgroundColor(int RGB) { mBackgroundColor = RGB; invalidate(); }
    public int getBackgroundColor()         { return mBackgroundColor; }
    public void setTextSize(float size)     { mTextSize = size; invalidate(); }
    public float getTextSize()              { return mTextSize; }

    @Override
    public void onClick(View view) {
        mOnClickListener.onClick(view);
    }
    public void setOnClickListener(@Nullable OnClickListener l) {
        if (!isClickable()) {
            setClickable(true);
        }
        mOnClickListener = l;
    }
}
