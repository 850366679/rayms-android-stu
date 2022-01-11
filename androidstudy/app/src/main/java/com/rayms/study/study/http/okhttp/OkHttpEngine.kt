package com.rayms.study.study.http.okhttp

import android.content.Context
import android.os.Handler
import android.os.Looper
import okhttp3.Cache
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit.SECONDS

class OkHttpEngine private constructor(context: Context) {
  private var mOkHttpClient: OkHttpClient? = null
  private var mHandler: Handler? = null

  companion object {
    private var instance: OkHttpEngine? = null
    fun getInstance(context: Context): OkHttpEngine {
      if (instance == null) {
        synchronized(OkHttpEngine::class) {
          if (instance == null) instance = OkHttpEngine(context)
        }
      }
      return instance!!
    }
  }

  init {
    val sdcache = context.externalCacheDir
    val cacheSize = 10 * 1024 * 1024L
    mOkHttpClient = OkHttpClient.Builder()
      .connectTimeout(15, SECONDS)
      .callTimeout(15, SECONDS)
      .writeTimeout(20, SECONDS)
      .readTimeout(20, SECONDS)
      .cache(Cache(sdcache!!.absoluteFile, cacheSize)).build()
    mHandler = Handler(Looper.getMainLooper())
  }

  /**
   * 异步GET请求
   */
  public fun getAsyncHttp(
    url: String,
    callback: ResultCallback
  ) {
    val request = Request.Builder().url(url).build()
    val call = mOkHttpClient!!.newCall(request)
    dealResult(call, callback)
  }

  private fun dealResult(
    call: Call,
    callback: ResultCallback
  ) {
    call.enqueue(object : Callback{
      override fun onFailure(
        call: Call,
        e: IOException
      ) {
        sendFailCallback(call.request(), e, callback)
      }

      override fun onResponse(
        call: Call,
        response: Response
      ) {
        senSuccessCallback(response.body.toString(), callback)
      }

      private fun senSuccessCallback(str: String, callback: ResultCallback){
        mHandler?.post {
          try {
            callback.onResponse(str)
          } catch (e: IOException) {
            e.printStackTrace()
          }
        }
      }

      private fun sendFailCallback(request: Request, e: Exception, callback: ResultCallback) {
        callback.onError(request, e)
      }
    })
  }
}