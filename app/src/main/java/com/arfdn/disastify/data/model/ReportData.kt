package com.arfdn.disastify.data.model


import com.google.gson.annotations.SerializedName

data class ReportData(
    @SerializedName("flood_depth")
    val floodDepth: Int = 0,
    @SerializedName("report_type")
    val reportType: String = ""
)