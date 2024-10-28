package com.example.travelapp.domain.usecase

import com.example.travelapp.data.model.TravelResponse
import com.example.travelapp.data.repository.TravelRepository
import javax.inject.Inject

class TravelUseCase @Inject constructor(private val travelRepository: TravelRepository) {
    suspend fun getTravel(token: String, page: Int): TravelResponse {
        return travelRepository.getTravel(token, page)
    }

}