package com.rayms.mydesign.myview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.rayms.mydesign.R

/**
 * @auther RayMouns
 * @date 2022/2/23
 * @time 19:22.
 */
class TitleBar : RelativeLayout {
  private var ivBack: ImageView? = null
  private var ivMenu: ImageView? = null
  private var tvTitle: TextView? = null
  private var rlRoot: RelativeLayout? = null
  private val tvColor = Color.WHITE
  private val bgColor = Color.BLUE

  constructor(context: Context) : super(context) {
    initView(context)
  }

  constructor(
    context: Context,
    attributeSet: AttributeSet?
  ) : super(context, attributeSet) {
    initView(context)
  }

  constructor(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttr: Int
  ) : super(context, attributeSet, defStyleAttr) {
    initView(context)
  }

  private fun initView(context: Context) {
    LayoutInflater.from(context).inflate(R.layout.view_custom_title, this, true)
    tvTitle = findViewById(R.id.tv_title)
    ivBack = findViewById(R.id.iv_back)
    ivMenu = findViewById(R.id.iv_menu)
    rlRoot = findViewById(R.id.rl_root)
    rlRoot?.setBackgroundColor(bgColor)
    tvTitle?.setTextColor(tvColor)
  }

  fun setTitle(title: String) {
    tvTitle?.text = title
  }

  fun setLeftListener(onClickListener: OnClickListener) {
    ivBack?.setOnClickListener(onClickListener)
  }

  fun setRightListener(onClickListener: OnClickListener) {
    ivMenu?.setOnClickListener(onClickListener)
  }
}