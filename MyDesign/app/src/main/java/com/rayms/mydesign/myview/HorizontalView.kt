package com.rayms.mydesign.myview

import android.content.Context
import android.os.Message
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_MOVE
import android.view.MotionEvent.ACTION_UP
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.widget.Scroller
import kotlin.math.abs

/**
 * @auther RayMouns
 * @date 2022/2/23
 * @time 20:40.
 */
class HorizontalView : ViewGroup {
  private var lastInterceptX = 0f
  private var lastInterceptY = 0f
  private var lastX = 0f
  private var lastY = 0f
  private var currentIndex = 0
  private var childWidth = 0
  private var mScroller: Scroller? = null
  private var tracker: VelocityTracker? = null

  constructor(context: Context) : super(context) {
    init()
  }

  constructor(
    context: Context,
    attrs: AttributeSet?
  ) : super(context, attrs) {
    init()
  }

  constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int
  ) : super(context, attrs, defStyleAttr) {
    init()
  }

  private fun init() {
    mScroller = Scroller(context)
    tracker = VelocityTracker.obtain()
  }

  override fun onMeasure(
    widthMeasureSpec: Int,
    heightMeasureSpec: Int
  ) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
    val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
    val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
    val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)

    measureChildren(widthMeasureSpec, heightMeasureSpec)
    if (childCount == 0) {
      setMeasuredDimension(0, 0)
    } else if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
      val childAt = if (childCount > 0) getChildAt(0) else null
      val childWidth = childAt?.measuredWidth ?: 0
      val childHeight: Int = childAt?.measuredHeight ?: 0
      setMeasuredDimension(childWidth * childCount, childHeight)
    } else if (widthSpecMode == MeasureSpec.AT_MOST) {
      val childAt = if (childCount > 0) getChildAt(0) else null
      val childWidth = childAt?.measuredWidth ?: 0
      setMeasuredDimension(childWidth * childCount, heightSpecSize)
    } else if (heightSpecMode == MeasureSpec.AT_MOST) {
      val childAt = if (childCount > 0) getChildAt(0) else null
      val childHeight: Int = childAt?.measuredHeight ?: 0
      setMeasuredDimension(widthSpecSize, childHeight)
    }
  }

  override fun onLayout(
    changed: Boolean,
    l: Int,
    t: Int,
    r: Int,
    b: Int
  ) {
    var left = 0
    var child: View? = null
    for (index in 0 until childCount) {
      child = getChildAt(index)
      if (child.visibility != GONE) {
        childWidth = child.measuredWidth
        child.layout(left, 0, left + child.measuredWidth, measuredHeight)
        left += child.measuredWidth
      }
    }
  }

  override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
    var intercept = false
    val x = ev?.x ?: 0f
    val y = ev?.y ?: 0f
    when (ev?.action) {
      ACTION_DOWN -> {
        intercept = false
        if (mScroller?.isFinished == false) {
          mScroller?.abortAnimation()
        }
      }
      ACTION_MOVE -> {
        val deltaX = x - lastInterceptX
        val deltaY = y - lastInterceptY
        intercept = abs(deltaX) - abs(deltaY) > 0 // 我自己处理
      }
      ACTION_UP -> {
        intercept = false
      }
    }
    lastX = x
    lastY = y
    lastInterceptX = x
    lastInterceptY = y
    return intercept
  }

  override fun onTouchEvent(event: MotionEvent?): Boolean {
    tracker?.addMovement(event)
    val x = event?.x ?: 0f
    val y = event?.y ?: 0f
    when (event?.action) {
      ACTION_DOWN -> {
        if (mScroller?.isFinished == false) {
          mScroller?.abortAnimation()
        }
      }
      ACTION_MOVE -> {
        val deltaX = x - lastX
        scrollBy(-deltaX.toInt(), 0)
      }
      ACTION_UP -> {
        println(scrollX)
        val distance = scrollX - currentIndex * childWidth
        if (abs(distance) > childWidth / 2) {
          if (distance > 0) {
            currentIndex++
          } else {
            currentIndex--
          }
        } else {
          tracker?.computeCurrentVelocity(1000)
          val xV = tracker?.xVelocity ?: 0f
          if (abs(xV) > 50) {
            if (xV > 0) {
              currentIndex--
            } else {
              currentIndex++
            }
          }
        }
        currentIndex =
          when {
            currentIndex < 0 -> 0
            currentIndex > childCount - 1 -> childCount - 1
            else -> currentIndex
          }
        smoothScrollTo(currentIndex * childWidth, 0)
        tracker?.clear()
      }
    }
    lastX = x
    lastY = y
    return super.onTouchEvent(event)
  }

  override fun computeScroll() {
    super.computeScroll()
    if (mScroller?.computeScrollOffset() == true) {
      scrollTo(mScroller!!.currX, mScroller!!.currY)
      postInvalidate()
    }
  }

  private fun smoothScrollTo(
    destX: Int,
    destY: Int
  ) {
    mScroller?.startScroll(scrollX, scrollY, destX - scrollX, destY - scrollY, 1000)
    invalidate()
  }
}