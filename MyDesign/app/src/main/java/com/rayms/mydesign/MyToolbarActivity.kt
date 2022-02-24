package com.rayms.mydesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.rayms.mydesign.myview.TitleBar

class MyToolbarActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_my_toolbar)
    val tb = findViewById<TitleBar>(R.id.tb_mine)
    tb.setTitle("我的测试数据")
    tb.setLeftListener {
      finish()
    }
    tb.setRightListener() {

    }
  }
}