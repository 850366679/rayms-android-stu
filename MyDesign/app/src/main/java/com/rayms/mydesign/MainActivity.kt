package com.rayms.mydesign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
  private var btn: Button? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    btn = findViewById<Button>(R.id.btn_snackbar)
    btn?.setOnClickListener {
      showSnackbar()
    }
    findViewById<Button>(R.id.btn_input).setOnClickListener {
      startActivity(Intent(this, TextInputActivity::class.java))
    }
    findViewById<Button>(R.id.btn_custom).setOnClickListener {
      startActivity(Intent(this, MyHorizontalActivity::class.java))
    }
    findViewById<Button>(R.id.btn_flow).setOnClickListener {
      startActivity(Intent(this, MyFlowActivity::class.java))
    }
  }

  private fun showSnackbar() {
    Snackbar.make(btn!!, "标题", Snackbar.LENGTH_LONG).setAction("点击事件") {
      Toast.makeText(this, "Toast", Toast.LENGTH_SHORT).show()
    }.setDuration(Snackbar.LENGTH_LONG).show()
  }
}