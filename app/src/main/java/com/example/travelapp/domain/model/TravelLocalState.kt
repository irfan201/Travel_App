package com.example.travelapp.domain.model

import com.example.travelapp.data.model.TravelEntity
import com.example.travelapp.data.model.TravelResponse

sealed class TravelLocalState {
    object Loading: TravelLocalState()
    data class Success(val data: List<TravelEntity>): TravelLocalState()
    data class Error(val message: String): TravelLocalState()

}