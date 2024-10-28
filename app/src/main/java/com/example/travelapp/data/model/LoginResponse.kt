package com.example.travelapp.data.model


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
) {
    data class Data(
        @SerializedName("avatar")
        val avatar: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("firstName")
        val firstName: String,
        @SerializedName("lastName")
        val lastName: String,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("role")
        val role: String,
        @SerializedName("token")
        val token: String
    )
}