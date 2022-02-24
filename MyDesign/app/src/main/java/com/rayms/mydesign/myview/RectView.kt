package com.rayms.mydesign.myview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.rayms.mydesign.R

/**
 * @auther RayMouns
 * @date 2022/2/23
 * @time 18:51.
 */
class RectView : View {
  private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
  private var mColor = Color.RED

  constructor(context: Context) : super(context) {
    initPaint()
  }

  constructor(
    context: Context,
    attrs: AttributeSet?
  ) : super(context, attrs) {
    val mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RectView)
    mColor = mTypedArray.getColor(R.styleable.RectView_rect_color, Color.RED)
    mTypedArray.recycle() // 回收资源
    initPaint()
  }

  constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int
  ) : super(context, attrs, defStyleAttr) {
    initPaint()
  }

  private fun initPaint() {
    mPaint.color = mColor
    mPaint.strokeWidth = 1.5f
  }

  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)
    canvas?.drawRect(
      0f + paddingLeft, 0f + paddingTop, width * 1f - paddingRight, height * 1f - paddingBottom,
      mPaint
    )
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
    if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
      setMeasuredDimension(600, 600)
    } else if (widthSpecMode == MeasureSpec.AT_MOST) {
      setMeasuredDimension(600, heightSpecSize)
    } else if (heightSpecMode == MeasureSpec.AT_MOST) {
      setMeasuredDimension(widthSpecSize, 600)
    }
  }
}