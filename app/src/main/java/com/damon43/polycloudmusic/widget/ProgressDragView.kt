package com.damon43.polycloudmusic.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.damon43.common.commonutils.DensityUtil
import com.damon43.polycloudmusic.R

/**
 * desc 进度拖动view
 * Created by lenovo on 2018/1/3.
 */
class ProgressDragView : View {

    var mWidth = 0
    var mHeight = 0
    var mDefaultHeight = 0
    var mDefaultWidth = 0
    var mContext: Context
    lateinit var mStartedPaint: Paint
    lateinit var mReadyPaint: Paint
    var pointX = 0f
    var mDrawLineY: Float = 0f
    var bigCircleRadius = 0f
    var smallCircleRadius = 0f
    var mEndLineX = 0f
    var mStartX = 0f

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        mContext = context!!
        init()
    }

    fun init() {
        mDefaultHeight = DensityUtil.dip2px(mContext, 20f)
        mDefaultWidth = DensityUtil.dip2px(mContext, 180f)

        mStartedPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mReadyPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mStartedPaint.color = mContext.resources.getColor(R.color.primary_dark)
        mReadyPaint.color = mContext.resources.getColor(R.color.primary_light)
        mStartedPaint.strokeWidth = DensityUtil.dip2px(mContext, 5f).toFloat()
        mReadyPaint.strokeWidth = DensityUtil.dip2px(mContext, 2f).toFloat()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMeasureMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMeasureMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val measureWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        val measureHeight = View.MeasureSpec.getSize(heightMeasureSpec)

        if (widthMeasureMode == View.MeasureSpec.AT_MOST) {
            mWidth = if (measureWidth > mDefaultWidth) mDefaultWidth else measureWidth
        } else if (widthMeasureMode == View.MeasureSpec.EXACTLY) {
            mWidth = measureWidth
        }
        if (heightMeasureMode == View.MeasureSpec.AT_MOST) {
            mHeight = if (measureHeight > mDefaultHeight) mDefaultHeight else measureHeight
        } else if (widthMeasureMode == View.MeasureSpec.EXACTLY) {
            mHeight = measureHeight
        }

        mDrawLineY = mHeight / 2.toFloat()
        bigCircleRadius = mDrawLineY / 2 + 8
        smallCircleRadius = bigCircleRadius - 5
        mStartX = bigCircleRadius
        mEndLineX = mWidth - bigCircleRadius
        pointX = mStartX
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas!!.drawLine(mStartX, mDrawLineY, pointX, mDrawLineY, mStartedPaint)
        canvas.drawLine(pointX, mDrawLineY, mEndLineX, mDrawLineY, mReadyPaint)
        canvas.drawCircle(pointX, mDrawLineY, bigCircleRadius, mReadyPaint)
        canvas.drawCircle(pointX, mDrawLineY, smallCircleRadius, mStartedPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action = event!!.action
        if (event.x in mStartX..mEndLineX) when (action) {
            MotionEvent.ACTION_DOWN -> performClick()
            MotionEvent.ACTION_MOVE -> scroll(event.x)
        }

        return true
    }

    fun scroll(x: Float) {
        pointX = x
        invalidate()
    }
}