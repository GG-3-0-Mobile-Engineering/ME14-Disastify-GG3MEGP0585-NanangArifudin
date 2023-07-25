package com.arfdn.disastify.data.model


import com.google.gson.annotations.SerializedName

data class Output(
    @SerializedName("geometries")
    val geometries: List<Geometry> = listOf(),
    @SerializedName("type")
    val type: String = ""
)