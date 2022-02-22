package com.rayms.mydesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
  private var btn: View? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    btn = findViewById<Button>(R.id.btn_snackbar)
    findViewById<Button>(R.id.btn_snackbar).setOnClickListener {
      showSnackbar()
    }
  }

  private fun showSnackbar() {
    Snackbar.make(btn!!, "标题", Snackbar.LENGTH_LONG).setAction("点击事件") {
      Toast.makeText(this, "Toast", Toast.LENGTH_SHORT).show()
    }.setDuration(Snackbar.LENGTH_LONG).show()
  }
}