package com.buildinglink.dictionary.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.buildinglink.dictionary.data.database.model.DatabasePhonetic


@Dao
interface PhoneticDao {

    @Query("SELECT * FROM phonetics WHERE word = :query")
    fun getDefinition(query: String): List<DatabasePhonetic>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhonetics(phonetics: List<DatabasePhonetic>)

    @Query("DELETE FROM phonetics where word = :query")
    fun deleteDefinition(query: String)
}
