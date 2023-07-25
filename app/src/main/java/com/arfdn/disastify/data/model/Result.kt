package com.arfdn.disastify.data.model


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("arcs")
    val arcs: List<Any> = listOf(),
    @SerializedName("bbox")
    val bbox: List<Double> = listOf(),
    @SerializedName("objects")
    val objects: Objects = Objects(),
    @SerializedName("type")
    val type: String = ""
)