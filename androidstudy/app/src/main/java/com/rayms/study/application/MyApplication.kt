package com.rayms.study.application

import android.app.Application
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class MyApplication: Application() {
  private var mQueue : RequestQueue? = null

  fun getMQueue() : RequestQueue? {
    return mQueue
  }

  override fun onCreate() {
    super.onCreate()
    mQueue = Volley.newRequestQueue(this)
  }
}