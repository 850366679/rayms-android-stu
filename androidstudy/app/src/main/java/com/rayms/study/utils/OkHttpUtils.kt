package com.rayms.study.utils

import okhttp3.OkHttpClient
import okhttp3.Request


val client = OkHttpClient()

fun get(url: String) {
    val request = Request.Builder().url(url).build()
    val newCall = client.newCall(request)
    val response = newCall.execute()
    val body = response.body
    println("-------------> ${body?.string()}")
}