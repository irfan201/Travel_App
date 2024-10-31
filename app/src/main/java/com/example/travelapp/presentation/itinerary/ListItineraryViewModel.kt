package com.example.travelapp.presentation.itinerary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.domain.model.TravelLocalState
import com.example.travelapp.domain.usecase.TravelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListItineraryViewModel @Inject constructor(private val travelUseCase: TravelUseCase):ViewModel() {
    private val _travelState = MutableStateFlow<TravelLocalState>(TravelLocalState.Loading)
    val travelState: StateFlow<TravelLocalState> = _travelState

     fun getAllTravel(){
        viewModelScope.launch {
            try {
                val data = travelUseCase.getAllTravel()
                _travelState.value = TravelLocalState.Success(data)
            } catch (e:Exception){
                _travelState.value = TravelLocalState.Error(e.message ?: "unknown error")
            }
        }
    }
}