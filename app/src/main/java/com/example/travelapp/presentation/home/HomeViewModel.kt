package com.example.travelapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.domain.model.TravelState
import com.example.travelapp.domain.model.User
import com.example.travelapp.domain.usecase.TravelUseCase
import com.example.travelapp.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val travelUseCase: TravelUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _travelState = MutableStateFlow<TravelState>(TravelState.Loading)
    val travelState: StateFlow<TravelState> = _travelState

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> get() = _user




    suspend fun getTravelType(token: String, page: Int, type: String) {

            try {
                val response = travelUseCase.getTravelType(token, page, type)
                _travelState.value = TravelState.Success(response)
            } catch (e: Exception) {
                _travelState.value = TravelState.Error(e.message ?: "Unknown error")
            }

    }

    suspend fun saveType(type: String) {
        userUseCase.saveType(type)
    }

    fun getType(): String? {
        return userUseCase.getType()
    }

    fun getProfile() {
        _user.value = userUseCase.getProfile()
    }

    fun getLogin(): String? {
        return userUseCase.getLogin()
    }
}