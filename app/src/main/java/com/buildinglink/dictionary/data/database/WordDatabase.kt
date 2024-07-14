package com.buildinglink.dictionary.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.buildinglink.dictionary.data.database.dao.PhoneticDao
import com.buildinglink.dictionary.data.database.model.DatabasePhonetic

@Database(entities = [DatabasePhonetic::class], version = 1)
abstract class WordDatabase : RoomDatabase() {
    abstract fun phoneticDao(): PhoneticDao
}