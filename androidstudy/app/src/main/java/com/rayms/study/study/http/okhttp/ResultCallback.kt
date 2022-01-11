package com.rayms.study.study.http.okhttp

import okhttp3.Request
import java.io.IOException

abstract class ResultCallback {
  abstract fun onError(
    request: Request,
    e: Exception
  )

  @Throws(IOException::class) abstract fun onResponse(str: String)
}