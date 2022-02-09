package com.rayms.study

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.rayms.study.ui.MyOkHttpActivity
import com.rayms.study.ui.MyRetrofitActivity
import com.rayms.study.ui.MyVolleyActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    initViews()
  }

  /**
   * 载入views
   */
  private fun initViews() {
    findViewById<Button>(R.id.btn_okhttp).setOnClickListener(this)
    findViewById<Button>(R.id.btn_volley).setOnClickListener(this)
  }

  override fun onClick(v: View?) {
    when (v?.id) {
      R.id.btn_okhttp -> {
        startActivity(Intent(this, MyOkHttpActivity::class.java))
      }
      R.id.btn_volley -> {
        startActivity(Intent(this, MyVolleyActivity::class.java))
      }
    }
  }

  fun retrofitUseClick(view: View) {
    startActivity(Intent(this, MyRetrofitActivity::class.java))
  }
}