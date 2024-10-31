package com.example.travelapp.data.model


import com.google.gson.annotations.SerializedName

data class TravelResponse(
    @SerializedName("data")
    val `data`: List<DataTravel>,
    @SerializedName("message")
    val message: String,
    @SerializedName("page")
    val page: Int,
    @SerializedName("perPage")
    val perPage: Int,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("todalData")
    val todalData: Int,
    @SerializedName("totalPage")
    val totalPage: Int
) {
    data class DataTravel(
        @SerializedName("activity")
        val activity: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("duration")
        val duration: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: String,
        @SerializedName("location")
        val location: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("popularity")
        val popularity: String,
        @SerializedName("type")
        val type: String
    )
}