package com.damon43.polycloudmusic.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.damon43.polycloudmusic.R

/**
 * Created by lenovo on 2017/8/18.
 */
class BottomSildeLayout : LinearLayout {
    var mContext: Context? = null
    //默认高度
    var defultHeight = 0

    var theLayoutParams: LinearLayout.LayoutParams? = null

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
        viewTreeObserver.addOnGlobalLayoutListener {
            theLayoutParams = getLayoutParams() as
                    LayoutParams?
            theLayoutParams!!.setMargins(theLayoutParams!!.leftMargin, -
            defultHeight, theLayoutParams!!.rightMargin, theLayoutParams!!.bottomMargin)
            layoutParams = theLayoutParams
            visibility = View.VISIBLE
        }
    }

    private fun init() {

    }

    var downY = 0f

    val TAG = "BottomSildeLayout"

    override
    fun onTouchEvent(event: MotionEvent?): Boolean {
        val ex = event!!.rawX
        val ey = event!!.rawY

        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG, "event: down in" + "ex:" + ex + " ,ey:" + ey)
                downY = ey
                return false
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(TAG, "event: move in" + "ex:" + ex + " ,ey:" + ey)

                theLayoutParams!!.topMargin = (theLayoutParams!!.topMargin + ey - downY).toInt()
            }
            MotionEvent.ACTION_UP -> Log.d(TAG, "event: up in" + "ex:" + ex + " ,ey:" + ey)
        }
        return true
    }
}