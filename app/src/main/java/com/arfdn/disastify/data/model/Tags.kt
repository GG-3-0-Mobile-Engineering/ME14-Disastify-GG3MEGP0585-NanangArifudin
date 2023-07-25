package com.arfdn.disastify.data.model


import com.google.gson.annotations.SerializedName

data class Tags(
    @SerializedName("district_id")
    val districtId: Any? = Any(),
    @SerializedName("instance_region_code")
    val instanceRegionCode: String = "",
    @SerializedName("local_area_id")
    val localAreaId: String? = "",
    @SerializedName("region_code")
    val regionCode: String = ""
)