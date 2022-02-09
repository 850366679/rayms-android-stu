package com.rayms.study.service

import com.rayms.study.bean.IpModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface GitHubService {
  @GET("getIpInfo.php?ip=59.108.54.37")
  fun getCityInfo(): Call<IpModel>
}