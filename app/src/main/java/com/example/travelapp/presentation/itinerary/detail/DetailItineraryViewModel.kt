package com.example.travelapp.presentation.itinerary.detail

import androidx.lifecycle.ViewModel
import com.example.travelapp.data.model.TravelEntity
import com.example.travelapp.domain.model.TravelLocalDetailState
import com.example.travelapp.domain.usecase.TravelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DetailItineraryViewModel @Inject constructor(private val travelUseCase: TravelUseCase) :ViewModel(){
    private val _travelDetail = MutableStateFlow<TravelLocalDetailState>(TravelLocalDetailState.Loading)
    val travelDetail: StateFlow<TravelLocalDetailState> get() = _travelDetail

    suspend fun deleteTravel(travel: TravelEntity) {
        travelUseCase.deleteTravel(travel)
    }

    suspend fun updateTravel(travel: TravelEntity) {
        travelUseCase.updateTravel(travel)
    }

    suspend fun getTravelById(id: Int) {
        try {
            val dataTravel = travelUseCase.getTravelById(id)
            _travelDetail.value = TravelLocalDetailState.Success(dataTravel)
        }catch (e:Exception){
            _travelDetail.value = TravelLocalDetailState.Error(e.message ?: "Unknown error")

        }
    }
}