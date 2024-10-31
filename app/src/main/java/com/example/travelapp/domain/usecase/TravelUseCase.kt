package com.example.travelapp.domain.usecase

import com.example.travelapp.data.model.TravelDetailResponse
import com.example.travelapp.data.model.TravelEntity
import com.example.travelapp.data.model.TravelResponse
import com.example.travelapp.data.repository.TravelRepository
import javax.inject.Inject

class TravelUseCase @Inject constructor(private val travelRepository: TravelRepository) {
    suspend fun getTravel(token: String, page: Int): TravelResponse {
        return travelRepository.getTravel(token, page)
    }

    suspend fun getTravelDetail(token: String, id: Int): TravelDetailResponse {
        return travelRepository.getTravelDetail(token, id)
    }

    suspend fun searchTravel(token: String, page: Int, name: String): TravelResponse {
        return travelRepository.searchTravel(token, page, name)
    }

    suspend fun getTravelType(token: String, page: Int, type: String): TravelResponse {
        return travelRepository.getTravelType(token, page, type)

    }

    suspend fun insertTravel(travel: TravelEntity) {
        travelRepository.insertTravel(travel)

    }

    suspend fun getAllTravel(): List<TravelEntity> {
        return travelRepository.getAllTravel()
    }

    suspend fun deleteTravel(travel: TravelEntity) {
        travelRepository.deleteTravel(travel)
    }

    suspend fun getTravelById(id: Int): TravelEntity {
        return travelRepository.getTravelById(id)
    }

    suspend fun updateTravel(travel: TravelEntity){
        travelRepository.updateTravel(travel)
    }

}