package com.example.travelapp.presentation.profile

import androidx.lifecycle.ViewModel
import com.example.travelapp.data.model.LoginResponse
import com.example.travelapp.domain.model.UserState
import com.example.travelapp.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userUseCase: UserUseCase):ViewModel() {

    private val _userState = MutableStateFlow<UserState>(UserState.Loading)
    val userState: StateFlow<UserState> get() = _userState

    suspend fun logout(){
        userUseCase.logout()
    }

     fun getProfile() {
        try {
            _userState.value = UserState.Success(userUseCase.getProfile())
        } catch (e: Exception) {
            _userState.value = UserState.Error(e.message ?: "Unknown error")
        }
    }



}