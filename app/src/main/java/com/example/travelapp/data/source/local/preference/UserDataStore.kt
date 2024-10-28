package com.example.travelapp.data.source.local.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.travelapp.data.model.LoginResponse
import com.example.travelapp.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

private const val USER_PREFERENCES_NAME = "user_preferences"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES_NAME)

class UserDataStore @Inject constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun saveLogin(token: String) {
        dataStore.edit { preference ->
            preference[TOKEN] = token
        }
    }

    suspend fun saveProfile(userData: User) {
        dataStore.edit { preference ->
            preference[AVATAR] = userData.avatar
            preference[EMAIL] = userData.email
            preference[FIRST_NAME] = userData.firstName
            preference[LAST_NAME] = userData.lastName
            preference[PHONE] = userData.phone
            preference[TOKEN] = userData.token
        }
    }

    fun getProfile(): User {
        return runBlocking(Dispatchers.IO) {
            dataStore.data.map {
                User(
                    avatar = it[AVATAR] ?: "",
                    email = it[EMAIL] ?: "",
                    firstName = it[FIRST_NAME] ?: "",
                    lastName = it[LAST_NAME] ?: "",
                    phone = it[PHONE] ?: "",
                    token = it[TOKEN] ?: ""
                )
            }.first()
        }
    }

    fun getLogin(): String? {
        return runBlocking(Dispatchers.IO) {
            dataStore.data.first()[TOKEN]
        }
    }

    suspend fun logout() {
        dataStore.edit { preference ->
            preference.remove(TOKEN)
        }
    }

    companion object {

        val TOKEN = stringPreferencesKey("token")
        val AVATAR = stringPreferencesKey("avatar")
        val EMAIL = stringPreferencesKey("email")
        val FIRST_NAME = stringPreferencesKey("first_name")
        val LAST_NAME = stringPreferencesKey("last_name")
        val PHONE = stringPreferencesKey("phone")


    }
}