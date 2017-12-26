package com.damon43.polycloudmusic.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.damon43.common.commonutils.DensityUtil
import com.damon43.polycloudmusic.R
import com.damon43.polycloudmusic.event.MusicEvent
import com.damon43.polycloudmusic.theinterface.OnBottomPlayStateListener

/**
 * Created by lenovo on 2017/8/18.
 */
class BottomSildeLayout : LinearLayout {
    var heightListener: OnBottomPlayStateListener? = null
    var mContext: Context? = null
    //默认高度
    var minHeight = 0
    var listener: ViewTreeObserver.OnGlobalLayoutListener? = null
    var theLayoutParams: RelativeLayout.LayoutParams? = null
    var maxHeight = 0
    var foldHeight = 0
    var FOLDED_SIZE = 0
    var heightInterval = 0
    var alphaInterval = 0
    var vt: VelocityTracker? = null
    var downY: Float = 0f
    var downMargin: Int? = 0
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

    /**顶部面板的覆盖阴影*/
    var mAboveShadow: Drawable? = null
    var mShadowHeight = 0

    constructor(con: Context) : super(con) {
        mContext = con
        init()
    }

    constructor(con: Context, attr: AttributeSet) : this(con, attr, 0)

    constructor(con: Context, attr: AttributeSet, i: Int) : super(con, attr, i) {
        mContext = con
        val ta = con.obtainStyledAttributes(attr, R.styleable.BottomSildeLayout)
        minHeight = ta.getDimensionPixelSize(R.styleable.BottomSildeLayout_defult_height, 0)
        ta.recycle()
        init()
        initListener()
    }

    private fun initListener() {
        listener = ViewTreeObserver.OnGlobalLayoutListener {
            theLayoutParams = getLayoutParams() as
                    RelativeLayout.LayoutParams?
            maxHeight = theLayoutParams!!.height

            foldHeight = -(maxHeight - minHeight)
            theLayoutParams!!.setMargins(theLayoutParams!!.leftMargin,
                    theLayoutParams!!.topMargin, theLayoutParams!!.rightMargin, foldHeight)

            heightInterval = (maxHeight - minHeight) / 8 //  8个级别的透明度
            spreadHeightLevel1 = -(minHeight + heightInterval)
            spreadHeightLevel2 = -(spreadHeightLevel1 + heightInterval)
            spreadHeightLevel3 = -(spreadHeightLevel2 + heightInterval)
            spreadHeightLevel4 = -(spreadHeightLevel3 + heightInterval)
            spreadHeightLevel5 = -(spreadHeightLevel4 + heightInterval)
            spreadHeightLevel6 = -(spreadHeightLevel5 + heightInterval)
            spreadHeightLevel7 = -(spreadHeightLevel6 + heightInterval)
            FOLDED_SIZE = foldHeight / 2
            visibility = View.VISIBLE
            -maxHeight + heightInterval
            viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
        viewTreeObserver.addOnGlobalLayoutListener(listener)
        alphaInterval = 255 / 6
    }

    private fun init() {
        vt = VelocityTracker.obtain()
        mMaxVelocity = ViewConfiguration.get(mContext).scaledMaximumFlingVelocity.toFloat()
        mAboveShadow = mContext?.resources?.getDrawable(R.drawable.above_shadow)
        mShadowHeight = DensityUtil.dip2px(mContext,5f)
    }

    override fun performClick(): Boolean {
        return super.performClick()
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
                    downMargin = theLayoutParams?.bottomMargin
                    Log.d(TAG, "downMargin:$downMargin");
                }
                MotionEvent.ACTION_MOVE -> {
//                    val xOffect = ((downMargin?.plus(ey))?.minus(downY))?.toInt()
                    val xOffect = (downMargin?.minus(ey?.minus(downY)))?.toInt()
                    Log.d(TAG, "offect:$xOffect 最大的margin值：${foldHeight}")
                    if (xOffect in foldHeight..0) {
                        theLayoutParams?.bottomMargin = xOffect
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
                        if (theLayoutParams?.bottomMargin in FOLDED_SIZE..0) {
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
        val currentMargin = theLayoutParams!!.bottomMargin
        val closeAnim = ValueAnimator.ofInt(currentMargin, foldHeight)
        closeAnim.duration = when (currentMargin) {
            in foldHeight..(foldHeight - FOLDED_SIZE) / 2 -> SPREAD_LEVEL_1_DURA
            in (foldHeight - FOLDED_SIZE) / 2..FOLDED_SIZE -> SPREAD_LEVEL_2_DURA
            else -> Math.abs(((maxHeight + currentMargin) / speedY).toLong())
        }
        closeAnim.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                val size: Int? = animation?.animatedValue as Int
                Log.d(TAG, "size:$size")
                if (size != null) {
                    theLayoutParams!!.bottomMargin = size
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
        closeAnim.interpolator = DecelerateInterpolator()
        closeAnim.start()
    }

    fun openDrawer() {
        val currentMargin = theLayoutParams!!.bottomMargin
        val openAnim = ValueAnimator.ofInt(currentMargin, 0)
        openAnim.duration = when (currentMargin) {
            in FOLDED_SIZE + minHeight / 3..0 -> SPREAD_LEVEL_2_DURA
            in FOLDED_SIZE..FOLDED_SIZE + minHeight / 3 -> SPREAD_LEVEL_1_DURA
            else -> Math.abs(((maxHeight + currentMargin) / speedY).toLong())
        }

        Log.d(TAG, "duration:${openAnim.duration}")
        openAnim.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                val size: Int? = animation?.animatedValue as Int
                Log.d(TAG, "size:$size")
                if (size != null) {
                    theLayoutParams!!.bottomMargin = size
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

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mAboveShadow?.setBounds(left,0,right,mShadowHeight)
        mAboveShadow?.draw(canvas)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        releaseVelocityTracker()
    }

}