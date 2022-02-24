package com.rayms.mydesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class MyHorizontalActivity : AppCompatActivity() {
  private var lvOne: ListView? = null
  private var lvTwo: ListView? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_my_horizontal)
    initViews()
  }

  private fun initViews() {
    lvOne = findViewById(R.id.lv_one)
    lvTwo = findViewById(R.id.lv_two)
    val arrayOfOne = arrayOf(
      "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
      "18", "19", "20", "21", "1123"
    )
    val oneAdapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, arrayOfOne)
    lvOne?.adapter = oneAdapter
    val arrayOfTwo = arrayOf(
      "A", "B", "C", "F", "E", "G", "AFSF", "HJJ", "O", "U", "Y", "T", "DFG", "AS", "VB", "YFC",
      "SD", "DSD", "DSDS", "DS", "SS"
    )
    val twoAdapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, arrayOfTwo)
    lvTwo?.adapter = twoAdapter
  }
}