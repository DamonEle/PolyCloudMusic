package com.damon43.polycloudmusic.widget

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import com.damon43.polycloudmusic.R
import com.damon43.polycloudmusic.theinterface.OnBottomPlayStateListener

/**
 * Created by lenovo on 2017/8/18.
 */
class BottomSildeLayout : LinearLayout {
    var heightListener: OnBottomPlayStateListener? = null
    var mContext: Context? = null
    //默认高度
    var defultHeight = 0
    var listener: ViewTreeObserver.OnGlobalLayoutListener? = null
    var theLayoutParams: LinearLayout.LayoutParams? = null
    var maxHeight = 0
    var FOLDED_SIZE = 0
    var heightInterval = 0
    var alphaInterval = 0
    var vt: VelocityTracker? = null
    var downY: Float = 0f
    var downTopMargin: Int? = 0
    var lastY = 0f
    val TAG = "BottomSildeLayout"
    var lastTime = 0L
    var speedY: Float = 0f
    var mPointId = 0
    var mMaxVelocity = 0f
    var spreadHeightLevel1 = 0
    var spreadHeightLevel2 = 0
    var spreadHeightLevel3 = 0
    var spreadHeightLevel4 = 0
    var spreadHeightLevel5 = 0
    var spreadHeightLevel6 = 0
    var spreadHeightLevel7 = 0
    var spreadHeightLevel8 = 0

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
            theLayoutParams!!.setMargins(theLayoutParams!!.leftMargin,
                    defultHeight, theLayoutParams!!.rightMargin, theLayoutParams!!.bottomMargin)
            maxHeight = theLayoutParams!!.height
            heightInterval = (maxHeight - defultHeight) / 8 //  8个级别的透明度
            spreadHeightLevel1 = -(defultHeight + heightInterval)
            spreadHeightLevel2 = -(spreadHeightLevel1 + heightInterval)
            spreadHeightLevel3 = -(spreadHeightLevel2 + heightInterval)
            spreadHeightLevel4 = -(spreadHeightLevel3 + heightInterval)
            spreadHeightLevel5 = -(spreadHeightLevel4 + heightInterval)
            spreadHeightLevel6 = -(spreadHeightLevel5 + heightInterval)
            spreadHeightLevel7 = -(spreadHeightLevel6 + heightInterval)
            FOLDED_SIZE = (maxHeight + defultHeight) / 2
            visibility = View.VISIBLE
            -maxHeight + heightInterval
            viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
        viewTreeObserver.addOnGlobalLayoutListener(listener)
        alphaInterval = 255 / 6
    }

    private fun init() {
        vt = VelocityTracker.obtain()
        mMaxVelocity = ViewConfiguration.get(mContext).scaledMaximumFlingVelocity.toFloat();
    }


    override
    fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null && theLayoutParams != null) {
            vt?.addMovement(event)
            val ey = event.rawY
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downY = ey
                    lastY = ey
                    lastTime = System.currentTimeMillis()
                    downTopMargin = theLayoutParams?.topMargin
                }
                MotionEvent.ACTION_MOVE -> {
                    val xOffect = ((downTopMargin?.plus(ey))?.minus(downY))?.toInt()
                    if (xOffect in -maxHeight..-defultHeight) {
                        theLayoutParams?.topMargin = xOffect
                        layoutParams = theLayoutParams
                        heightListener?.onChangeDegree(when (xOffect) {
                            in -maxHeight..spreadHeightLevel7 -> 0
                            in spreadHeightLevel7..spreadHeightLevel6 -> alphaInterval * 1
                            in spreadHeightLevel6..spreadHeightLevel5 -> alphaInterval * 2
                            in spreadHeightLevel5..spreadHeightLevel4 -> alphaInterval * 3
                            in spreadHeightLevel4..spreadHeightLevel3 -> alphaInterval * 4
                            in spreadHeightLevel3..spreadHeightLevel2 -> alphaInterval * 5
                            in spreadHeightLevel2..spreadHeightLevel1 -> alphaInterval * 6
                            else -> 0
                        })
                    }
                }
                MotionEvent.ACTION_UP -> {
                    vt?.computeCurrentVelocity(1, 3f)
                    speedY = vt?.getYVelocity() ?: 1f
                    if (speedY == 0f) speedY = mMaxVelocity / 20000
                    Log.d(TAG, "speedY:$speedY")

                    if (speedY < -0.4) {
                        openDrawer()
                    } else if (speedY > 0.4) {
                        closeDrawer()
                    } else {
                        if (theLayoutParams?.topMargin in -maxHeight..-FOLDED_SIZE) {
                            openDrawer()
                        } else {
                            closeDrawer()
                        }
                    }
                }
            }
            return true
        }
        return false

    }

    private val SPREAD_LEVEL_1_DURA = 100L
    private val SPREAD_LEVEL_2_DURA = 200L

    private fun closeDrawer() {
        val currentMargin = theLayoutParams!!.topMargin
        val openAnim = ValueAnimator.ofInt(currentMargin, -defultHeight)
        openAnim.duration = when (currentMargin) {
            in -(3 * defultHeight) / 2..-defultHeight -> SPREAD_LEVEL_1_DURA
            in -FOLDED_SIZE..-(3 * defultHeight) / 2 -> SPREAD_LEVEL_2_DURA
            else -> Math.abs(((maxHeight + currentMargin) / speedY).toLong())
        }
        openAnim.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                val size: Int? = animation?.animatedValue as Int
                Log.d(TAG, "size:$size")
                if (size != null) {
                    theLayoutParams!!.topMargin = size
                    layoutParams = theLayoutParams
                    heightListener?.onChangeDegree(when (size) {
                        in -maxHeight..spreadHeightLevel7 -> 0
                        in spreadHeightLevel7..spreadHeightLevel6 -> alphaInterval * 1
                        in spreadHeightLevel6..spreadHeightLevel5 -> alphaInterval * 2
                        in spreadHeightLevel5..spreadHeightLevel4 -> alphaInterval * 3
                        in spreadHeightLevel4..spreadHeightLevel3 -> alphaInterval * 4
                        in spreadHeightLevel3..spreadHeightLevel2 -> alphaInterval * 5
                        in spreadHeightLevel2..spreadHeightLevel1 -> alphaInterval * 6
                        else -> 0
                    })
                }
            }
        })
        openAnim.interpolator = DecelerateInterpolator()
        openAnim.start()
    }

    fun openDrawer() {
        val currentMargin = theLayoutParams!!.topMargin
        val openAnim = ValueAnimator.ofInt(currentMargin, -maxHeight)
        openAnim.duration = when (currentMargin) {
            in -FOLDED_SIZE - defultHeight / 3..-FOLDED_SIZE -> SPREAD_LEVEL_2_DURA
            in -maxHeight..-FOLDED_SIZE - defultHeight / 3 -> SPREAD_LEVEL_1_DURA
            else -> Math.abs(((maxHeight + currentMargin) / speedY).toLong())
        }

        Log.d(TAG, "duration:${openAnim.duration}")
        openAnim.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                val size: Int? = animation?.animatedValue as Int
                Log.d(TAG, "size:$size")
                if (size != null) {
                    theLayoutParams!!.topMargin = size
                    layoutParams = theLayoutParams
                    heightListener?.onChangeDegree(when (size) {
                        in -maxHeight..spreadHeightLevel7 -> 0
                        in spreadHeightLevel7..spreadHeightLevel6 -> alphaInterval * 1
                        in spreadHeightLevel6..spreadHeightLevel5 -> alphaInterval * 2
                        in spreadHeightLevel5..spreadHeightLevel4 -> alphaInterval * 3
                        in spreadHeightLevel4..spreadHeightLevel3 -> alphaInterval * 4
                        in spreadHeightLevel3..spreadHeightLevel2 -> alphaInterval * 5
                        in spreadHeightLevel2..spreadHeightLevel1 -> alphaInterval * 6
                        else -> 0
                    })
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

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        releaseVelocityTracker()
    }
}