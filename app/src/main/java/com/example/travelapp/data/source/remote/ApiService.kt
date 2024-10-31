package com.example.travelapp.data.source.remote

import com.example.travelapp.data.model.LoginRequest
import com.example.travelapp.data.model.LoginResponse
import com.example.travelapp.data.model.TravelDetailResponse
import com.example.travelapp.data.model.TravelResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @GET("travel")
    suspend fun getTravel(
        @Header("Authorization") token: String,
        @Query("page") page: Int? = null,
        @Query("name") name: String? = null,
        @Query("type") type: String? = null,
    ): TravelResponse



    @GET("travel/{travel_id}")
    suspend fun getTravelDetail(
        @Header("Authorization") token: String,
        @Path("travel_id") id: Int
    ): TravelDetailResponse
}