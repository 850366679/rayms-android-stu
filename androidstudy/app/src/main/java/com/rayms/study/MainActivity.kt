package com.rayms.study

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.rayms.study.ui.MyOkHttpActivity

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
  }

  override fun onClick(v: View?) {
    when (v?.id) {
      R.id.btn_okhttp -> {
        startActivity(Intent(this, MyOkHttpActivity::class.java))
      }
    }
  }
}