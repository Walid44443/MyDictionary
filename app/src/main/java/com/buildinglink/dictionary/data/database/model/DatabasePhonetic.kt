package com.buildinglink.dictionary.data.database.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phonetics")
data class DatabasePhonetic(
    @PrimaryKey(autoGenerate = false)
    val text: String,
    val word: String,
    val audio: String,
)
