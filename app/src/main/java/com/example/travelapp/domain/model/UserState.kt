package com.example.travelapp.domain.model

import com.example.travelapp.data.model.LoginResponse

sealed class UserState {
    object Loading:UserState()
    data class Success(val userData: User):UserState()
    data class Error(val message: String):UserState()
}