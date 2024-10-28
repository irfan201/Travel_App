package com.example.travelapp.data.source.local

import com.example.travelapp.data.source.local.preference.UserDataStore
import com.example.travelapp.domain.model.User
import javax.inject.Inject

interface LocalDataSource {
    suspend fun saveLogin(token: String)
    fun getLogin(): String?
    suspend fun logout()
    suspend fun saveProfile(userData: User)
     fun getProfile(): User
}

class LocalDataSourceImpl @Inject constructor(private val dataStore: UserDataStore): LocalDataSource{
    override suspend fun saveLogin(token: String) {
        dataStore.saveLogin(token)
    }

    override fun getLogin(): String? {
        return dataStore.getLogin()
    }

    override suspend fun logout() {
        dataStore.logout()
    }

    override suspend fun saveProfile(userData: User) {
         dataStore.saveProfile(userData)
    }

    override fun getProfile(): User {
        return dataStore.getProfile()

    }

}