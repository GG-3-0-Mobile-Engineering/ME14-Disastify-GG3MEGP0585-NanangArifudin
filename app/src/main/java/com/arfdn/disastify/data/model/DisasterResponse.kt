package com.arfdn.disastify.data.model


import com.google.gson.annotations.SerializedName

data class DisasterResponse(
    @SerializedName("result")
    val result: Result? = Result(),
    @SerializedName("statusCode")
    val statusCode: Int? = 0
)