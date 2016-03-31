package com.cat14.demo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 *
 */
public class ViewPagerIndicator extends LinearLayout {

    private Paint mPaint;                   // 画笔
    private Path mPath;                     // 路径

    private int mTriangleWidth;             // 三角形宽
    private int mTriangleHeight;            // 三角形高

    // 三角形底边宽度
    private static final float RADIO_TRIANGLE_WIDTH = 1 / 6F;

    private int mInitTranslationX;          // 初始化位置X
    private int mTranslationX;              // 三角形位移X

    private int mTabVisibleCount;
    private static final int COUNT_DEFAULT_TABLE = 3;

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 获取可见Tab的数量
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);

        mTabVisibleCount = typedArray.getInt(R.styleable.ViewPagerIndicator_visible_tab_count, COUNT_DEFAULT_TABLE);

        if (mTabVisibleCount < 0) {
            mTabVisibleCount = COUNT_DEFAULT_TABLE;
        }

        typedArray.recycle();                           // 释放


        // 初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);                       // 设置抗锯齿
        mPaint.setColor(Color.parseColor("#ffffff"));    // 白色
        mPaint.setStyle(Paint.Style.FILL);               // 填充
        mPaint.setPathEffect(new CornerPathEffect(3));   // 设置圆角效果
    }

    /**
     * 根据屏幕信息设置控件的大小
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 根据控件宽高设置 w/3:每个控件的宽度
        mTriangleWidth = (int) (w / 3 * RADIO_TRIANGLE_WIDTH);
        // 初始时偏移量 w/3/2:每个控件的宽度的一半
        mInitTranslationX = w / 3 / 2 - mTriangleWidth / 2;
        // 初始化三角形高度
        mTriangleHeight = mTriangleWidth / 2;

        initTriangle();
    }

    /**
     * 初始化三角形
     */
    private void initTriangle() {
        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();

        canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 3);
        canvas.drawPath(mPath, mPaint);

        canvas.restore();
        super.dispatchDraw(canvas);
    }

    /**
     * 指示器跟随手指进行滚动
     */
    public void scroll(int position, float offset) {
        int tabWidth = getWidth() / 3;
        mTranslationX = (int) (tabWidth * (offset + position));
        invalidate();                                   // 进行重绘
    }

    /**
     * 当XML加载完成后调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }
}
