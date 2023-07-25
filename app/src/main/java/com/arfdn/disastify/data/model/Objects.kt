package com.arfdn.disastify.data.model


import com.google.gson.annotations.SerializedName

data class Objects(
    @SerializedName("output")
    val output: Output = Output()
)