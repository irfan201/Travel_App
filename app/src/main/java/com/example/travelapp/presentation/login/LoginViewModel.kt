package com.example.travelapp.presentation.login

import androidx.lifecycle.ViewModel
import com.example.travelapp.data.model.LoginRequest
import com.example.travelapp.data.model.LoginResponse
import com.example.travelapp.domain.model.User
import com.example.travelapp.domain.model.UserState
import com.example.travelapp.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userUseCase: UserUseCase):ViewModel() {
    private val _userState = MutableStateFlow<UserState>(UserState.Loading)
    val userState:StateFlow<UserState> get() = _userState

    suspend fun login(loginRequest: LoginRequest){
        try {
            _userState.value = UserState.Success(userUseCase.login(loginRequest))
        }catch (e:Exception){
            _userState.value = UserState.Error(e.message ?: "Unknown error")
        }
    }
    suspend fun saveLogin(token:String){
        userUseCase.saveLogin(token)
    }

    suspend fun saveProfile(userData: User){
        userUseCase.saveProfile(userData)

    }

    fun getLogin():String?{
        return userUseCase.getLogin()

    }

}