package com.example.travelapp.presentation.detail

import androidx.lifecycle.ViewModel
import com.example.travelapp.domain.model.TravelDetailState
import com.example.travelapp.domain.usecase.TravelUseCase
import com.example.travelapp.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val travelUseCase: TravelUseCase,private val userUseCase: UserUseCase) : ViewModel() {
    private val _travelDetail = MutableStateFlow<TravelDetailState>(TravelDetailState.Loading)
    val travelDetail: StateFlow<TravelDetailState> get() = _travelDetail

     suspend fun getTravelDetail(token: String, id: Int){
        try {
            _travelDetail.value = TravelDetailState.Success(travelUseCase.getTravelDetail(token, id))
        }catch (e:Exception){
            _travelDetail.value = TravelDetailState.Error(e.message ?: "Unknown error")
        }
    }

    fun getToken(): String? {
        return userUseCase.getLogin()
    }
}