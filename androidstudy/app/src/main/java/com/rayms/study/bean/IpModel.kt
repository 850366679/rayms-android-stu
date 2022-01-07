package com.rayms.study.bean

data class IpModel(
    val code: Int,
    val `data`: Data,
    val msg: String
)

data class Data(
    val area: String,
    val area_id: String,
    val city: String,
    val city_id: String,
    val country: String,
    val country_id: String,
    val county: String,
    val county_id: Any,
    val ip: String,
    val isp: String,
    val isp_id: String,
    val queryIp: String,
    val region: String,
    val region_id: String
)