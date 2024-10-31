package com.example.travelapp.domain.model

import com.example.travelapp.data.model.TravelEntity

sealed class TravelLocalDetailState {
    object Loading: TravelLocalDetailState()
    data class Success(val data: TravelEntity): TravelLocalDetailState()
    data class Error(val message: String): TravelLocalDetailState()
}