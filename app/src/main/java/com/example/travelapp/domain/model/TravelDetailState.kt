package com.example.travelapp.domain.model

import com.example.travelapp.data.model.TravelDetailResponse

sealed class TravelDetailState {
    object Loading: TravelDetailState()
    data class Success(val data: TravelDetailResponse): TravelDetailState()
    data class Error(val message: String): TravelDetailState()
}