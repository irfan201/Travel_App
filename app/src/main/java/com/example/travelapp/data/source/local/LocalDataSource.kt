package com.example.travelapp.data.source.local

import com.example.travelapp.data.model.TravelEntity
import com.example.travelapp.data.source.local.preference.UserDataStore
import com.example.travelapp.data.source.local.room.TravelDao
import com.example.travelapp.domain.model.User
import javax.inject.Inject

interface LocalDataSource {
    suspend fun saveLogin(token: String)
    fun getLogin(): String?
    suspend fun logout()
    suspend fun saveProfile(userData: User)
    fun getProfile(): User
    suspend fun insertTravel(travel: TravelEntity)
    suspend fun getAllTravel(): List<TravelEntity>
    suspend fun getTravelById(id: Int): TravelEntity
    suspend fun deleteTravel(travel: TravelEntity)
    suspend fun saveStart(start: Boolean)
    fun getStart(): Boolean
    suspend fun saveType(type: String)
    fun getType(): String?
    suspend fun updateTravel(travel: TravelEntity)
}

class LocalDataSourceImpl @Inject constructor(private val dataStore: UserDataStore,private val travelDao: TravelDao) :
    LocalDataSource {
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

    override suspend fun insertTravel(travel: TravelEntity) {
        travelDao.insertTravel(travel)
    }

    override suspend fun getAllTravel(): List<TravelEntity> {
        return travelDao.getAllTravel()
    }

    override suspend fun getTravelById(id: Int): TravelEntity {
        return travelDao.getTravelById(id)
    }

    override suspend fun deleteTravel(travel: TravelEntity) {
        travelDao.deleteTravel(travel)
    }

    override suspend fun saveStart(start: Boolean) {
        dataStore.saveStart(start)
    }

    override fun getStart(): Boolean {
        return dataStore.getStart()
    }

    override suspend fun saveType(type: String) {
        dataStore.saveType(type)
    }

    override fun getType(): String? {
        return dataStore.getType()
    }

    override suspend fun updateTravel(travel: TravelEntity) {
        travelDao.updateTravel(travel)
    }

}