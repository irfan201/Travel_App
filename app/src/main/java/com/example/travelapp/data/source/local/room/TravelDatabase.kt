package com.example.travelapp.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.travelapp.data.model.TravelEntity

@Database(entities = [TravelEntity::class], version = 1, exportSchema = false)
abstract class TravelDatabase:RoomDatabase() {
    abstract fun travelDao(): TravelDao

    companion object{
        @Volatile
        private var INSTANCE: TravelDatabase? = null

        fun getInstance(context: Context): TravelDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TravelDatabase::class.java,
                    "travel_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }


}