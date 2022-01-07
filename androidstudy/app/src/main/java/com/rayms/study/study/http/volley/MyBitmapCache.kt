package com.rayms.study.study.http.volley

import android.graphics.Bitmap
import com.android.volley.toolbox.ImageLoader.ImageCache

class MyBitmapCache() : ImageCache {
  override fun getBitmap(url: String?): Bitmap? {
    return null
  }

  override fun putBitmap(
    url: String?,
    bitmap: Bitmap?
  ) {
  }
}