package com.example.travelapp.data.repository

import com.example.travelapp.data.model.LoginRequest
import com.example.travelapp.data.model.LoginResponse
import com.example.travelapp.data.source.local.LocalDataSource
import com.example.travelapp.data.source.local.preference.UserDataStore
import com.example.travelapp.data.source.remote.RemoteDataSource
import com.example.travelapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserRepository {
    suspend fun login(loginRequest: LoginRequest): User
    suspend fun saveLogin(token: String)
    suspend fun logout()
    fun getLogin(): String?
    suspend fun saveProfile(userData: User)
    fun getProfile(): User
    suspend fun saveStart(start: Boolean)
    fun getStart(): Boolean
    suspend fun saveType(type: String)
    fun getType(): String?
}

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) :
    UserRepository {
    override suspend fun login(loginRequest: LoginRequest): User {
        return remoteDataSource.login(loginRequest).data.toUser()
    }

    override suspend fun saveLogin(token: String) {
        localDataSource.saveLogin(token)
    }

    override suspend fun logout() {
        localDataSource.logout()
    }

    override fun getLogin(): String? {
        return localDataSource.getLogin()
    }

    override suspend fun saveProfile(userData: User) {
        localDataSource.saveProfile(userData)
    }

    override fun getProfile(): User {
        return localDataSource.getProfile()
    }

    override suspend fun saveStart(start: Boolean) {
        localDataSource.saveStart(start)
    }

    override fun getStart(): Boolean {
        return localDataSource.getStart()
    }

    override suspend fun saveType(type: String) {
        localDataSource.saveType(type)
    }

    override fun getType(): String? {
        return localDataSource.getType()
    }


    fun LoginResponse.Data.toUser(): User {
        return User(
            avatar = this.avatar,
            email = this.email,
            firstName = this.firstName,
            lastName = this.lastName,
            phone = this.phone,
            token = this.token
        )
    }
}