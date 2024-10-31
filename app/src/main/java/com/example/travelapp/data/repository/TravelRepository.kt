package com.example.travelapp.data.repository

import com.example.travelapp.data.model.TravelDetailResponse
import com.example.travelapp.data.model.TravelEntity
import com.example.travelapp.data.model.TravelResponse
import com.example.travelapp.data.source.local.LocalDataSource
import com.example.travelapp.data.source.remote.RemoteDataSource
import javax.inject.Inject

interface TravelRepository {
    suspend fun getTravel(token: String, page: Int): TravelResponse
    suspend fun getTravelDetail(token: String, id: Int): TravelDetailResponse
    suspend fun searchTravel(token: String, page: Int, name: String): TravelResponse
    suspend fun getTravelType(token: String, page: Int, type: String): TravelResponse
    suspend fun insertTravel(travel: TravelEntity)
    suspend fun getAllTravel(): List<TravelEntity>
    suspend fun deleteTravel(travel: TravelEntity)
    suspend fun getTravelById(id: Int): TravelEntity
    suspend fun updateTravel(travel: TravelEntity)
}

class TravelRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : TravelRepository {
    override suspend fun getTravel(token: String, page: Int): TravelResponse {
        return remoteDataSource.getTravel(token, page)
    }

    override suspend fun getTravelDetail(token: String, id: Int): TravelDetailResponse {
        return remoteDataSource.getTravelDetail(token, id)
    }

    override suspend fun searchTravel(token: String, page: Int, name: String): TravelResponse {
        return remoteDataSource.searchTravel(token, page, name)
    }

    override suspend fun getTravelType(token: String, page: Int, type: String): TravelResponse {
        return remoteDataSource.getTravelType(token, page, type)
    }

    override suspend fun insertTravel(travel: TravelEntity) {
        localDataSource.insertTravel(travel)
    }

    override suspend fun getAllTravel(): List<TravelEntity> {
        return localDataSource.getAllTravel()
    }

    override suspend fun deleteTravel(travel: TravelEntity) {
        localDataSource.deleteTravel(travel)
    }

    override suspend fun getTravelById(id: Int): TravelEntity {
        return localDataSource.getTravelById(id)
    }

    override suspend fun updateTravel(travel: TravelEntity) {
        localDataSource.updateTravel(travel)
    }

}