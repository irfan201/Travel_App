package com.example.travelapp.domain.usecase

import com.example.travelapp.data.model.LoginRequest
import com.example.travelapp.data.model.LoginResponse
import com.example.travelapp.data.repository.UserRepository
import com.example.travelapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun login(loginRequest: LoginRequest): User {
        return userRepository.login(loginRequest)
    }

    suspend fun saveLogin(token: String) {
        userRepository.saveLogin(token)
    }

    suspend fun logout() {
        userRepository.logout()
    }

    fun getLogin(): String? {
        return userRepository.getLogin()

    }

    suspend fun saveProfile(userData: User) {
        userRepository.saveProfile(userData)

    }

    fun getProfile(): User {
        return userRepository.getProfile()
    }

    suspend fun saveStart(start: Boolean) {
        userRepository.saveStart(start)
    }

    fun getStart(): Boolean {
        return userRepository.getStart()
    }

    suspend fun saveType(type: String) {
        userRepository.saveType(type)

    }

    fun getType(): String? {
        return userRepository.getType()
    }
}
