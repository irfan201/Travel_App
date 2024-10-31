package com.example.travelapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "travel")
data class TravelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val image: String,
    val location: String,
    val popularity: String,
    val type: String,
    val activity: String,
    val description: String,
    val note: String
)