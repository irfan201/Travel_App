package com.example.travelapp.data.source.remote

import com.example.travelapp.data.model.LoginRequest
import com.example.travelapp.data.model.LoginResponse
import com.example.travelapp.data.model.TravelResponse
import javax.inject.Inject


interface RemoteDataSource {
    suspend fun login(loginRequest: LoginRequest):LoginResponse
    suspend fun getTravel(token:String,page:Int): TravelResponse
}

class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService):RemoteDataSource{
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return apiService.login(loginRequest)
    }

    override suspend fun getTravel(token: String, page: Int): TravelResponse {
        return apiService.getTravel(token,page)
    }

}