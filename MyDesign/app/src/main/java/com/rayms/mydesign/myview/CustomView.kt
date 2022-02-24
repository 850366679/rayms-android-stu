package com.rayms.mydesign.myview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller

/**
 * @auther RayMouns
 * @date 2022/2/22
 * @time 15:21.
 */
class CustomView : View {
  private var lastX: Int = 0
  private var lastY: Int = 0
  private var mScroller: Scroller? = null

  constructor(
    context: Context
  ) : super(context)

  constructor(
    context: Context,
    attrs: AttributeSet?
  ) : super(context, attrs) {
    mScroller = Scroller(context)
  }

  override fun computeScroll() {
    super.computeScroll()
    if (mScroller?.computeScrollOffset() == true) {
      (parent as View).scrollTo(mScroller!!.currX, mScroller!!.currY)
      invalidate()
    }
  }

  fun smoothScrollTo(
    destX: Int,
    destY: Int
  ) {
    mScroller?.startScroll(scrollX, scrollY, destX - scrollX, destY - scrollY, 5000)
  }

  override fun onTouchEvent(event: MotionEvent?): Boolean {
    val x = event!!.x.toInt() // 不可能为null
    val y = event.y.toInt()

    when (event.action) {
      MotionEvent.ACTION_DOWN -> {
        lastX = x
        lastY = y
      }
      MotionEvent.ACTION_MOVE -> {
        val offSetX = x - lastX
        val offSetY = y - lastY
        // onlayout()
        offsetLeftAndRight(offSetX)
        offsetTopAndBottom(offSetY)
        // ParamsLayput()
        // (parent as View).scrollBy(-offSetX, -offSetY)
      }
    }
    return true
  }
}