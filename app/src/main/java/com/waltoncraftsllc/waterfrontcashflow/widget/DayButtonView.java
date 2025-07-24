package com.waltoncraftsllc.waterfrontcashflow.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.waltoncraftsllc.waterfrontcashflow.R;

public class DayButtonView extends View {
    private String mText;
    private float mTextSize;
    private int mTextColor;
    private int mBackgroundColor;
    private int mBorderColor;
    private float mBorderWidth;
    private String mTypeface;
    private final Paint mPaint = new Paint();
    private final Rect mRect = new Rect();

//    public DayButtonView copy(Context context) {
//        DayButtonView day = new DayButtonView(context);
//        day.mText = this.mText;
//        day.mTextSize = this.mTextSize;
//        day.mTextColor = this.mTextColor;
//        day.mBackgroundColor = this.mBackgroundColor;
//        day.mBorderColor = this.mBorderColor;
//        day.mBorderWidth = this.mBorderWidth;
//        day.mTypeface = this.mTypeface;
//        return day;
//    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        if ( attrs != null ) {
            try ( TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DayButtonView, 0, 0) ) {
                mBackgroundColor = ta.getColor(R.styleable.DayButtonView_android_background, 0x000000);
                mTextColor = ta.getColor(R.styleable.DayButtonView_android_textColor, 0x000000);
                mBorderColor = ta.getColor(R.styleable.DayButtonView_dayBorderColor, 0xFFFFFF);
                mBorderWidth = ta.getDimension(R.styleable.DayButtonView_dayBorderWidth, 5.0F);
                mText = ta.getString(R.styleable.DayButtonView_android_text);
                if ( mText == null  ||  mText.isEmpty() ) {
                    mText = "null";
                }
                mTextSize = ta.getDimension(R.styleable.DayButtonView_android_textSize, 20.0F);
                mTypeface = ta.getString(R.styleable.DayButtonView_android_fontFamily);

            } catch ( Exception e ) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        }
    }
    public DayButtonView(Context context) {
        super(context);
        init(context, null);
    }

    public DayButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

//    public DayButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(context, attrs);
//    }
//
//    public DayButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init(context, attrs);
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mPaint.setTextSize(mTextSize);
        float textWidth = mPaint.measureText(mText);

        int min_width = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int widget_width = resolveSizeAndState(min_width, widthMeasureSpec, 1);

        int min_height = MeasureSpec.getSize(widget_width) - (int)textWidth + getPaddingBottom() + getPaddingTop();
        int widget_height = resolveSizeAndState(min_height, heightMeasureSpec, 0);

        setMeasuredDimension(widget_width, widget_height);
    }

//    @Override
//    protected void onSizeChanged(int width, int height, int old_width, int old_height) {
//        super.onSizeChanged(width, height, old_width, old_height);
////        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
////        canvas = new Canvas(bitmap);
//    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        mPaint.reset();
//        canvas.save();

    //--- Fill box
        mPaint.setColor(mBackgroundColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.getClipBounds(mRect);
        canvas.drawRect(mRect, mPaint);

    //--- Draw box
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setColor(mBorderColor);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(mRect, mPaint);

//        Typeface typeface = paint.setTypeface(...);
        String text = (mText != null? mText: "DayButtonView");
        float textWidth = mPaint.measureText(text);
        float x = (getWidth() - textWidth) / 2;
        float y = (getHeight() / 2.0f) - ((mPaint.descent() + mPaint.ascent()) / 2.0f) ;
        mPaint.reset();
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setAntiAlias(true);
        canvas.drawText(text, x, y, mPaint);
//        canvas.restore();
    }

    public void setText(String text) {
        mText = text;
    }
    public String getText() {
        return mText;
    }
    public void setTextColor(int RGB) {
        mTextColor = RGB;
    }
//    public int getTextColor() {
//        return mTextColor;
//    }
    @Override
    public void setBackgroundColor(int color) {
        mBackgroundColor = color;
//        super.setBackgroundColor(color);
    }
//    public int getBackgroundColor() {
//        return mBackgroundColor;
//    }
//    public void setTextSize(float size) {
//        mTextSize = size;
//    }
//    public float getTextSize() {
//        return mTextSize;
//    }
//    public int getBorderColor() {
//        return mBorderColor;
//    }
//    public void setBorderColor(int mBorderColor) {
//        this.mBorderColor = mBorderColor;
//    }
//    public float getBorderWidth() {
//        return mBorderWidth;
//    }
//    public void setBorderWidth(float mBorderWidth) {
//        this.mBorderWidth = mBorderWidth;
//    }
//    public String getTypeface() {
//        return mTypeface;
//    }
//    public void setTypeface(String mTypeface) {
//        this.mTypeface = mTypeface;
//    }
}
