package com.damon43.polycloudmusic.widget

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
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

    }

    var downY: Float = 0f
    var MIN_SPREAD_SIZE: Float = -90f
    var downTopMargin: Int? = 0
    val isStops
    val TAG = "BottomSildeLayout"
    private val MOVE_TIME_LEVEL_MIN: Long = 100L
    private val MOVE_TIME_LEVEL_NORMAL: Long = 160L
    private val MOVE_TIME_LEVEL_MAX: Long = 210L
    var downTime = 0L
    override
    fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null && theLayoutParams != null) {
            val ey = event.rawY
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downY = ey
                    downTime = System.currentTimeMillis()
                    downTopMargin = theLayoutParams?.topMargin
                }
                MotionEvent.ACTION_MOVE -> {
                    val xOffect = ((downTopMargin?.plus(ey))?.minus(downY))?.toInt()
                    if (xOffect in -maxHeight..-defultHeight) {
                        theLayoutParams?.topMargin = xOffect
                        layoutParams = theLayoutParams
                    }
                }
                MotionEvent.ACTION_UP -> {
                    val upTime = System.currentTimeMillis()
                    Log.d(TAG, "TIME:${upTime - downTime},move:${ey - downY}")
                    if (theLayoutParams?.topMargin in -maxHeight..-FOLDED_SIZE || (upTime - downTime <
                            MOVE_TIME_LEVEL_MIN && ey - downY > MIN_SPREAD_SIZE)) {
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
        openAnim.duration = when (Math.abs(maxHeight + currentMargin)) {
            in 0..defultHeight / 2 -> MOVE_TIME_LEVEL_MIN
            in defultHeight / 2..defultHeight -> MOVE_TIME_LEVEL_NORMAL
            in defultHeight..defultHeight * 3 / 2 -> MOVE_TIME_LEVEL_MAX
            else -> MOVE_TIME_LEVEL_NORMAL
        }
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
        openAnim.start()
    }
}