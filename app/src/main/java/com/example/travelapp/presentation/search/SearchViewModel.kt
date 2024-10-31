package com.example.travelapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.data.model.TravelResponse
import com.example.travelapp.domain.model.TravelState
import com.example.travelapp.domain.usecase.TravelUseCase
import com.example.travelapp.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val travelUseCase: TravelUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val _travelState = MutableStateFlow<TravelState>(TravelState.Loading)
    val travelState: StateFlow<TravelState> = _travelState

    suspend fun searchTravel(token: String, page: Int, name: String) {
        viewModelScope.launch {
            try {
                val response = travelUseCase.searchTravel(token, page, name)
                _travelState.value = TravelState.Success(response)
            } catch (e: Exception) {
                _travelState.value = TravelState.Error(e.message ?: "Unknown error")
            }
        }

    }

    fun getToken(): String? {
        return userUseCase.getLogin()
    }
}