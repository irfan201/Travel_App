package com.example.travelapp.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.travelapp.data.model.TravelEntity

@Dao
interface TravelDao {

    @Insert
    suspend fun insertTravel(travel: TravelEntity)

    @Query("SELECT * FROM travel")
    suspend fun getAllTravel(): List<TravelEntity>

    @Delete
    suspend fun deleteTravel(travel: TravelEntity)

    @Query("SELECT * FROM travel WHERE id = :id")
    suspend fun getTravelById(id: Int): TravelEntity

    @Update
    suspend fun updateTravel(travel: TravelEntity)

}