package com.example.travelapp.data.source.remote

import com.example.travelapp.data.model.LoginRequest
import com.example.travelapp.data.model.LoginResponse
import com.example.travelapp.data.model.TravelDetailResponse
import com.example.travelapp.data.model.TravelResponse
import javax.inject.Inject


interface RemoteDataSource {
    suspend fun login(loginRequest: LoginRequest): LoginResponse
    suspend fun getTravel(token: String, page: Int): TravelResponse
    suspend fun getTravelDetail(token: String, id: Int): TravelDetailResponse
    suspend fun searchTravel(token: String, page: Int, name: String): TravelResponse
    suspend fun getTravelType(token: String, page: Int, type: String): TravelResponse
}

class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    RemoteDataSource {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return apiService.login(loginRequest)
    }

    override suspend fun getTravel(token: String, page: Int): TravelResponse {
        return apiService.getTravel(token, page)
    }

    override suspend fun getTravelDetail(token: String, id: Int): TravelDetailResponse {
        return apiService.getTravelDetail(token, id)
    }

    override suspend fun searchTravel(token: String, page: Int, name: String): TravelResponse {
        return apiService.getTravel(token, page = page, name = name)
    }

    override suspend fun getTravelType(token: String, page: Int, type: String): TravelResponse {
        return apiService.getTravel(token, page = page, type = type)
    }

}