package com.example.travelapp.data.repository

import com.example.travelapp.data.model.TravelResponse
import com.example.travelapp.data.source.remote.RemoteDataSource
import javax.inject.Inject

interface TravelRepository {
    suspend fun getTravel(token: String, page: Int): TravelResponse

}

class TravelRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource):TravelRepository{
    override suspend fun getTravel(token: String, page: Int): TravelResponse {
        return remoteDataSource.getTravel(token,page)
    }

}