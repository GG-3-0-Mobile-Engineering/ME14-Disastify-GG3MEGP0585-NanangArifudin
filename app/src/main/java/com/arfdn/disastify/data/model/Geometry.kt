package com.arfdn.disastify.data.model


import com.google.gson.annotations.SerializedName

data class Geometry(
    @SerializedName("coordinates")
    val coordinates: List<Double?>? = listOf(),
    @SerializedName("properties")
    val properties: Properties? = Properties(),
    @SerializedName("type")
    val type: String? = ""
)