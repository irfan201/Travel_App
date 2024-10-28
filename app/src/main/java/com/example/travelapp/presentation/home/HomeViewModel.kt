package com.example.travelapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.domain.model.TravelState
import com.example.travelapp.domain.usecase.TravelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val travelUseCase: TravelUseCase):ViewModel() {

    private val _travelState = MutableStateFlow<TravelState>(TravelState.Loading)
    val travelState: StateFlow<TravelState> = _travelState


    fun getTravel(page:Int,token:String){
        viewModelScope.launch {
            try {
                val response = travelUseCase.getTravel(token,page)
                _travelState.value = TravelState.Success(response)
                }catch (e:Exception){
                _travelState.value = TravelState.Error(e.message.toString())
            }
        }
    }
}