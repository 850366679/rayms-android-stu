package com.rayms.mydesign.myview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView

/**
 * @auther RayMouns
 * @date 2022/2/23
 * @time 17:56.
 */
class InvalideTextView : androidx.appcompat.widget.AppCompatTextView {
  private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val mColor = Color.RED

  constructor(context: Context) : super(context) {
    initPaint()
  }

  constructor(
    context: Context,
    attrs: AttributeSet?
  ) : super(context, attrs) {
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
    canvas?.drawLine(0f, height / 2f, width * 1f, height / 2f, mPaint)
  }
}