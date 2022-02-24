package com.rayms.mydesign.myview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.MeasureSpec.EXACTLY
import android.view.ViewGroup
import java.util.ArrayList
import kotlin.math.max

/**
 * @auther RayMouns
 * @date 2022/2/24
 * @time 17:25.
 */
class FlowLayout : ViewGroup {
  private lateinit var viewList: ArrayList<ArrayList<View>>
  private lateinit var heightList: ArrayList<Int>
  private val mHorizontalSpacing = 32
  private val mVerticalSpacing = 16

  constructor(context: Context) : super(context)

  constructor(
    context: Context,
    attrs: AttributeSet?
  ) : super(context, attrs)

  constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int
  ) : super(context, attrs, defStyleAttr)

  private fun init() {
    heightList = arrayListOf()
    viewList = arrayListOf()
  }

  override fun onMeasure(
    widthMeasureSpec: Int,
    heightMeasureSpec: Int
  ) {
    init()

    val widthSize = MeasureSpec.getSize(widthMeasureSpec)
    val heightSize = MeasureSpec.getSize(heightMeasureSpec)
    val widthMode = MeasureSpec.getMode(widthMeasureSpec)
    val heightMode = MeasureSpec.getMode(heightMeasureSpec)

    var lineWidth = 0
    var lineHeight = 0
    var mViews = arrayListOf<View>()

    var realheight = 0
    var realWidth = 0

    for (index in 0 until childCount) {
      val childAt = getChildAt(index)

      val lp = childAt.layoutParams
      val childWidthSpec =
        getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, lp.width)
      val childHeightSpec =
        getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom, lp.height)

      childAt.measure(childWidthSpec, childHeightSpec)
      if (lineWidth + mHorizontalSpacing + childAt.measuredWidth > widthSize) {
        heightList.add(lineHeight)
        viewList.add(mViews)
        realheight += lineHeight
        realWidth = max(lineWidth, lineWidth)

        mViews = arrayListOf()
        lineWidth = 0
        lineHeight = 0
      }

      mViews.add(childAt)
      lineWidth += (mHorizontalSpacing + childAt.measuredWidth)
      lineHeight = max(lineHeight, childAt.measuredHeight)

      if (index == childCount - 1) {
        heightList.add(lineHeight)
        viewList.add(mViews)
        realheight += lineHeight
        realWidth = max(lineWidth, lineWidth)
      }
    }
    realheight = if (heightMode == EXACTLY) heightSize else realheight
    realWidth = if (widthMode == EXACTLY) widthSize else realWidth
    setMeasuredDimension(realWidth, realheight)
  }

  override fun onLayout(
    changed: Boolean,
    l: Int,
    t: Int,
    r: Int,
    b: Int
  ) {
    var curHeight = 0
    for (index in 0 until heightList.size) {
      val height = heightList[index]
      val arrayList = viewList[index]
      var curWidth = 0
      for (i in 0 until arrayList.size) {
        val view = arrayList[i]
        view.layout(
          mHorizontalSpacing + curWidth, curHeight + mVerticalSpacing,
          mHorizontalSpacing + curWidth + view.measuredWidth,
          curHeight + mVerticalSpacing + view.measuredHeight
        )
        curWidth += view.measuredWidth + mHorizontalSpacing
      }
      curHeight += height + mVerticalSpacing
    }
  }
}