package com.damon43.polycloudmusic.widget

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import com.damon43.polycloudmusic.R

/**
 * Created by lenovo on 2017/8/18.
 */
class BottomSildeLayout : LinearLayout {
    var mContext: Context? = null
    //默认高度
    var defultHeight = 0
    var listener: ViewTreeObserver.OnGlobalLayoutListener? = null
    var theLayoutParams: LinearLayout.LayoutParams? = null
    var maxHeight = 0
    var FOLDED_SIZE = 0

    var vt: VelocityTracker? = null
    var downY: Float = 0f
    var downTopMargin: Int? = 0
    var lastY = 0f
    val TAG = "BottomSildeLayout"
    var lastTime = 0L
    var speedY: Float = 0f
    var mPointId = 0
    var mMaxVelocity = 0f

    constructor(con: Context) : super(con) {
        mContext = con
        init()
    }

    constructor(con: Context, attr: AttributeSet) : this(con, attr, 0)

    constructor(con: Context, attr: AttributeSet, i: Int) : super(con, attr, i) {
        mContext = con
        val ta = con.obtainStyledAttributes(attr, R.styleable.BottomSildeLayout)
        defultHeight = ta.getDimensionPixelSize(R.styleable.BottomSildeLayout_defult_height, 0)
        ta.recycle()
        init()
        initListener()
    }

    private fun initListener() {
        listener = ViewTreeObserver.OnGlobalLayoutListener {
            theLayoutParams = getLayoutParams() as
                    LayoutParams?
            theLayoutParams!!.setMargins(theLayoutParams!!.leftMargin, -
            defultHeight, theLayoutParams!!.rightMargin, theLayoutParams!!.bottomMargin)
            maxHeight = theLayoutParams!!.height
            FOLDED_SIZE = (maxHeight - defultHeight) / 2
            visibility = View.VISIBLE
            viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
        viewTreeObserver.addOnGlobalLayoutListener(listener)


    }

    private fun init() {
        vt = VelocityTracker.obtain()
        mMaxVelocity = ViewConfiguration.get(mContext).scaledMaximumFlingVelocity.toFloat();
    }


    override
    fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null && theLayoutParams != null) {
            vt = VelocityTracker.obtain()
            vt?.addMovement(event)
            val ey = event.rawY
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downY = ey
                    lastY = ey
                    mPointId = event.getPointerId(0)
                    lastTime = System.currentTimeMillis()
                    downTopMargin = theLayoutParams?.topMargin
                }
                MotionEvent.ACTION_MOVE -> {
                    vt?.computeCurrentVelocity(1, mMaxVelocity / 10000)
                    speedY = vt?.getYVelocity(mPointId) ?: 1f
                    if (speedY == 0f) speedY = mMaxVelocity / 10000
                    Log.d(TAG, "speedY:$speedY")
                    val xOffect = ((downTopMargin?.plus(ey))?.minus(downY))?.toInt()
                    if (xOffect in -maxHeight..-defultHeight) {
                        theLayoutParams?.topMargin = xOffect
                        layoutParams = theLayoutParams
                    }
                }
                MotionEvent.ACTION_UP -> {
                    releaseVelocityTracker()
                    if (theLayoutParams?.topMargin in -maxHeight..-FOLDED_SIZE) {
                        openDrawer()
                    } else {

                    }
                }
            }
            return true
        }
        return false

    }

    fun openDrawer() {
        val currentMargin = theLayoutParams!!.topMargin
        val openAnim = ValueAnimator.ofInt(currentMargin, -maxHeight)
        openAnim.duration = Math.abs(((maxHeight + currentMargin) / speedY).toLong())
        Log.d(TAG, "duration:${openAnim.duration}");
        openAnim.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                val size: Int? = animation?.animatedValue as Int
                Log.d(TAG, "size:$size")
                if (size != null) {
                    theLayoutParams!!.topMargin = size
                    layoutParams = theLayoutParams
                }
            }
        })
        openAnim.interpolator = DecelerateInterpolator()
        openAnim.start()
    }

    //释放VelocityTracker

    private fun releaseVelocityTracker() {
        if (null != vt) {
            vt?.clear()
            vt?.recycle()
        }
    }
}