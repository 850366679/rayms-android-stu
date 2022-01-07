package com.rayms.study.ui

import android.os.Bundle
import android.os.Message
import android.util.Log.i
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rayms.study.R
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * OkHttp简单实现页面
 */
class MyOkHttpActivity : AppCompatActivity() {
  private val TAG = "MyOkHttpActivity.class"
  private val url = "https://www.baidu.com/"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_use_okhttp)
  }

  /**
   * 同步请求
   */
  fun onSyncClick(v: View) {
    Thread() {
      val client = OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build()
      val request = Request.Builder().url(url).get().build()
      val call = client.newCall(request)
      try {
        val response = call.execute()
        i(TAG, "response: ${response.body?.string()}")
      } catch (e: IOException) {
        e.printStackTrace();
      }
    }.start()
  }

  /**
   * 异步get请求
   */
  fun onAsyncGetClick(view: View) {
    val client = OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS).build()
    val request = Request.Builder().url(url).get().build()
    val call = client.newCall(request)
    call.enqueue(object : Callback {
      override fun onFailure(
        call: Call,
        e: IOException
      ) {
        val message = Message.obtain()
        i(TAG, "onFailure: ${message.obj}")
      }

      override fun onResponse(
        call: Call,
        response: Response
      ) {
        i(TAG, "response: ${response.body?.string()}");
      }
    })
  }

  /**
   * 异步post请求
   */
  fun onAsyncPostClick(view: View) {
    val postUrl = "http://api.k780.com/?app=weather.history"
    val client = OkHttpClient.Builder().build()
    val body = FormBody.Builder()
      .add("weaid", "1")
      .add("date", "2018-08-13")
      .add("appkey", "10003")
      .add("sign", "b59bc3ef6191eb9f747dd4e83c99f2a4")
      .add("format", "json")
      .build()
    val request = Request.Builder().url(postUrl).post(body).build()
    val call = client.newCall(request)
    call.enqueue(object : Callback {
      override fun onFailure(
        call: Call,
        e: IOException
      ) {
        i(TAG, "response: " + e.message.toString())
      }

      override fun onResponse(
        call: Call,
        response: Response
      ) {
        i(TAG, "response: " + response.body?.string())
      }
    })
  }
}