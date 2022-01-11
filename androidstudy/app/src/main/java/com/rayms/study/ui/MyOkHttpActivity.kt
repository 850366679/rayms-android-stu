package com.rayms.study.ui

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log.i
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rayms.study.R
import com.rayms.study.study.http.okhttp.OkHttpEngine
import com.rayms.study.study.http.okhttp.ResultCallback
import kotlinx.android.synthetic.main.activity_use_okhttp.iv_okhttp
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.TimeUnit.SECONDS

/**
 * OkHttp简单实现页面
 */
class MyOkHttpActivity : AppCompatActivity() {
  private val TAG = "MyOkHttpActivity.class"
  private val url = "https://www.baidu.com/"
  private val postUrl = "https://ip.taobao.com/service/getIpInfo.php"
  private val upFileUrl = "https://api.github.com/markdown/raw"
  private val downloadUrl =
    "https://upload-images.jianshu.io/upload_images/16810854-ce037079a21d028e.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240"
  private val multipartUrl = "https://api.imgur.com/3/image"
  private val MEDIA_TYPE_MARK_DOWN = "text/x-markdown; charset=utf-8".toMediaTypeOrNull()
  private val MEDIA_TYPE_PNG = "image/png".toMediaTypeOrNull()
  private val mHandler = Handler(Looper.getMainLooper())
  private val executor = Executors.newScheduledThreadPool(1)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_use_okhttp)
  }

  /**
   * 同步请求
   */
  fun onSyncClick(v: View) {
    Thread {
      val client = OkHttpClient.Builder().build()
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
    val client = getClientWithCache()
    val request = Request.Builder().url(url).get().build()
    val call = client.newCall(request)
    call.enqueue(object : Callback {
      override fun onFailure(
        call: Call,
        e: IOException
      ) {
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
    val client = OkHttpClient.Builder().build()
    val body = FormBody.Builder()
      .add("ip", "59.108.54.37")
      .build()
    val request = Request.Builder().url(postUrl).post(body).build()
    val call = client.newCall(request)
    call.enqueue(object : Callback {
      override fun onFailure(
        call: Call,
        e: IOException
      ) {
        i(TAG, "failMessage: ${e.message}")
      }

      override fun onResponse(
        call: Call,
        response: Response
      ) {
        i(TAG, "response: ${response.body?.string()}")
      }
    })
  }

  /**
   * 异步上传文件
   */
  fun onPostFileClick(view: View) {
    val file = createTxtFile() ?: return
    val client = OkHttpClient.Builder().build()
    val request =
      Request.Builder().url(upFileUrl).post(file.asRequestBody(MEDIA_TYPE_MARK_DOWN)).build()
    client.newCall(request).enqueue(object : Callback {
      override fun onFailure(
        call: Call,
        e: IOException
      ) {
        i(TAG, "fail message: ${e.message}")
      }

      override fun onResponse(
        call: Call,
        response: Response
      ) {
        i(TAG, "success message: ${response.body?.string()}")
      }
    })
  }

  /**
   * 新建TXT文件
   */
  private fun createTxtFile(): File? {
    var filePath: String? = null
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      filePath = getExternalFilesDir(null)?.path ?: return null
    } else {
      return null
    }
    // 创建一个文本文件， 里面的内容为OkHttp
    val file = File(filePath, "myokhttp.txt")
    if (!file.exists()) {
      file.createNewFile()
      try {
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write("OkHttp".toByteArray())
        fileOutputStream.close()
      } catch (e: IOException) {
        e.printStackTrace()
      }
    }
    return file
  }

  /**
   * 异步下载文件
   */
  fun onDownloadFileClick(view: View) {
    val client = OkHttpClient.Builder().build()
    val request = Request.Builder().url(downloadUrl).build()
    client.newCall(request).enqueue(object : Callback {
      override fun onFailure(
        call: Call,
        e: IOException
      ) {
        i(TAG, "fail message: ${e.message}")
      }

      override fun onResponse(
        call: Call,
        response: Response
      ) {
        try {
          val byteStream = response.body?.byteStream() ?: return
          val imgFile = createImgFile() ?: return
          val fileOutputStream = FileOutputStream(imgFile)
          val buffer = ByteArray(2048)
          var len = 0
          while (len.let {
              len = byteStream.read(buffer)
              len
            } != -1) {
            fileOutputStream.write(buffer, 0, len)
          }
          fileOutputStream.flush()
          fileOutputStream.close()
          mHandler.post {
            iv_okhttp.setImageURI(Uri.fromFile(imgFile))
          }
        } catch (e: IOException) {
          e.printStackTrace()
        }
      }
    })
  }

  /**
   * 新建图片文件
   */
  private fun createImgFile(): File? {
    var filePath: String? = null
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      filePath = getExternalFilesDir(null)?.path ?: return null
    } else {
      return null
    }
    // 创建一个文本文件， 里面的内容为OkHttp
    val file = File(filePath, "myokhttp.jpg")
    if (!file.exists()) {
      file.createNewFile()
    }
    return file
  }

  /**
   * 异步上传Multipart文件
   * 接口不可用
   */
  fun onMultipartFileClick(view: View) {
    val client = OkHttpClient.Builder().build()
    val asRequestBody = createImgFile()?.asRequestBody(MEDIA_TYPE_PNG) ?: return
    val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
      .addFormDataPart("title", "raymouns")
      .addFormDataPart("image", "myokhttp.jpg", asRequestBody)
      .build()
    val request = Request.Builder().header("Authorization", "Client-ID ...")
      .url(multipartUrl).post(requestBody).build()
    client.newCall(request).enqueue(object : Callback {
      override fun onFailure(
        call: Call,
        e: IOException
      ) {
        i(TAG, "fail message: ${e.message}")
      }

      override fun onResponse(
        call: Call,
        response: Response
      ) {
        i(TAG, "success message: ${response.body?.string()}")
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

  /**
   * 取消请求
   */
  fun onCancleRequestClick(view: View) {
    // .cacheControl(CacheControl.FORCE_NETWORK) 设置每次请求都请求网络
    val request = Request.Builder().url(url).cacheControl(CacheControl.FORCE_NETWORK).build()
    val client = OkHttpClient.Builder().build()
    val newCall = client.newCall(request)
    // 1000ms的情况下，每次都能请求成功，但是设置更小的（100ms）请求就会被cancle
    executor.schedule({
      newCall.cancel()
    }, 100, MILLISECONDS)
    newCall.enqueue(object : Callback {
      override fun onFailure(
        call: Call,
        e: IOException
      ) {
        i(TAG, "fail message: ${e.message}")
      }

      override fun onResponse(
        call: Call,
        response: Response
      ) {
        if (null != response.cacheResponse) {
          i(TAG, "cache---: ${response.cacheResponse.toString()}")
        } else {
          i(TAG, "network---: ${response.networkResponse.toString()}")
        }
      }
    })
  }

  /**
   * 封装的OkHttpEngine  GET请求url
   */
  fun onGetByEngineClick(view: View) {
    OkHttpEngine.getInstance(this).getAsyncHttp(url, object : ResultCallback() {
      override fun onError(
        request: Request,
        e: Exception
      ) {
        i(TAG, "fail message: ${e.message}")
      }

      override fun onResponse(str: String) {
        i(TAG, str)
        Toast.makeText(applicationContext, "请求成功", Toast.LENGTH_SHORT).show()
      }
    })
  }
}