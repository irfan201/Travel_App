package com.example.travelapp.data.model


import com.google.gson.annotations.SerializedName

data class TravelDetailResponse(
    @SerializedName("data")
    val `data`: TravelResponse.DataTravel,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)