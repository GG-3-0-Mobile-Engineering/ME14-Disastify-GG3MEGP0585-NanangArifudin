package com.arfdn.disastify.data.model


import com.google.gson.annotations.SerializedName

data class Properties(
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("disaster_type")
    val disasterType: String = "",
    @SerializedName("image_url")
    val imageUrl: String? = "",
    @SerializedName("partner_code")
    val partnerCode: Any? = Any(),
    @SerializedName("partner_icon")
    val partnerIcon: Any? = Any(),
    @SerializedName("pkey")
    val pkey: String = "",
    @SerializedName("report_data")
    val reportData: ReportData = ReportData(),
    @SerializedName("source")
    val source: String = "",
    @SerializedName("status")
    val status: String = "",
    @SerializedName("tags")
    val tags: Tags = Tags(),
    @SerializedName("text")
    val text: String = "",
    @SerializedName("title")
    val title: Any? = Any(),
    @SerializedName("url")
    val url: String = ""
)