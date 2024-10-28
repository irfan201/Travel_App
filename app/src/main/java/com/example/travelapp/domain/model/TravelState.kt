package com.example.travelapp.domain.model

import com.example.travelapp.data.model.TravelResponse

sealed class TravelState {
    object Loading: TravelState()
    data class Success(val data: TravelResponse): TravelState()
    data class Error(val message: String): TravelState()

}