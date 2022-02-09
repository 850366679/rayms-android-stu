package com.rayms.study.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rayms.study.R
import com.rayms.study.bean.IpModel
import com.rayms.study.service.GitHubService
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

class MyRetrofitActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_my_retrofit)
  }

  private fun init() {
    val okHttpClient = getClientWithCache()
    val retrofit = Retrofit.Builder()
      .baseUrl("https://ip.taobao.com/service/")
      .addConverterFactory(GsonConverterFactory.create())
      .client(okHttpClient)
      .build()
    val service = retrofit.create(GitHubService::class.java)
    val call = service.getCityInfo()
    call.enqueue(object : Callback<IpModel> {
      override fun onResponse(
        call: Call<IpModel>,
        response: Response<IpModel>
      ) {
        val body = response.body()
        println("---------------success")
        println("---${body.toString()}")
        if (body?.code ?: -1 == 0) {
          println("---${body?.data?.city ?: "没有此参数"}")
        }
      }

      override fun onFailure(
        call: Call<IpModel>,
        t: Throwable
      ) {
        println("---------------fail")
      }
    })
  }

  /**
   * 设置缓存
   */
  private fun getClientWithCache(): OkHttpClient {
    val maxSize = 100 * 1024 * 1024L
    return OkHttpClient.Builder()
      .connectTimeout(15, SECONDS)
      .callTimeout(15, SECONDS)
      .writeTimeout(20, SECONDS)
      .readTimeout(20, SECONDS)
      .cache(Cache(externalCacheDir!!.absoluteFile, maxSize))
      .build()
  }

  fun onRequestClick(view: View) {
    init()
  }
}