package com.rayms.study.ui

import android.graphics.Bitmap.Config.RGB_565
import android.os.Bundle
import android.widget.ImageView.ScaleType.CENTER_INSIDE
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.ImageLoader.ImageContainer
import com.android.volley.toolbox.ImageLoader.ImageListener
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.rayms.study.R
import com.rayms.study.application.MyApplication
import com.rayms.study.bean.IpModel
import com.rayms.study.study.http.volley.MyBitmapCache
import kotlinx.android.synthetic.main.activity_my_volley.btn_image_request
import kotlinx.android.synthetic.main.activity_my_volley.btn_image_request_imageloader
import kotlinx.android.synthetic.main.activity_my_volley.btn_json_request
import kotlinx.android.synthetic.main.activity_my_volley.btn_string_request
import kotlinx.android.synthetic.main.activity_my_volley.iv_volley
import kotlinx.android.synthetic.main.activity_my_volley.iv_volley2
import kotlinx.android.synthetic.main.activity_my_volley.iv_volley3

/**
 * volley简单使用
 */
class MyVolleyActivity : AppCompatActivity() {
  private var mQueue: RequestQueue? = null
  private val string_url = "https://www.baidu.com/"
  private val json_url = "https://ip.taobao.com/service/getIpInfo.php?ip=59.108.54.37"
  private val image_url =
    "https://upload-images.jianshu.io/upload_images/16810854-ce037079a21d028e.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_my_volley)
    mQueue = (application as MyApplication).getMQueue()
    btn_string_request.setOnClickListener {
      stringRequest()
    }
    btn_json_request.setOnClickListener {
      jsonRequest()
    }
    btn_image_request.setOnClickListener {
      imageRequest()
    }
    btn_image_request_imageloader.setOnClickListener {
      getImageByUrl()
    }
    networkImagegetImage()
  }

  /**
   * 字符串请求
   * 这里没有传method，默认GET请求
   */
  private fun stringRequest() {
    val stringRequest = StringRequest(string_url, {
      println(it ?: "null")
    }, {
      println(it.message ?: "null")
    })
    mQueue?.add(stringRequest)
  }

  /**
   * JsonRequest请求
   */
  private fun jsonRequest() {
    val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, json_url, null, {
      println(it.toString())
      if (it.getInt("code") == 0) {
        val model = Gson().fromJson(it.toString(), IpModel::class.java)
        println(model?.data?.city ?: "没有此参数")
      }
    }, {
      println(it.message ?: "null")
    })
    mQueue?.add(jsonObjectRequest)
  }

  /**
   * ImageRequest请求图片
   */
  private fun imageRequest() {
    val imageRequest = ImageRequest(image_url, {
      iv_volley.setImageBitmap(it)
    }, 0, 0, CENTER_INSIDE, RGB_565, {
      iv_volley.setImageResource(R.drawable.ic_default)
    })
    mQueue?.add(imageRequest)
  }

  /**
   * imageloader请求请求图片
   * 不同的是每次请求，都会先显示默认图片
   */
  private fun getImageByUrl() {
    val imageLoader = ImageLoader(mQueue, MyBitmapCache())
    imageLoader.get(image_url, object : ImageListener {
      override fun onErrorResponse(error: VolleyError?) {
        iv_volley2.setImageResource(R.drawable.ic_default)
      }

      override fun onResponse(
        response: ImageContainer?,
        isImmediate: Boolean
      ) {
        response?.bitmap?.apply {
          iv_volley2.setImageBitmap(this)
        } ?: iv_volley2.setImageResource(R.drawable.ic_default)
      }
    })
  }

  private fun networkImagegetImage(){
    val imageLoader = ImageLoader(mQueue, MyBitmapCache())
    iv_volley3.setDefaultImageResId(R.drawable.ic_default)
    iv_volley3.setErrorImageResId(R.drawable.ic_default)
    iv_volley3.setImageUrl(image_url, imageLoader)
  }
}