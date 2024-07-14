package com.buildinglink.dictionary.data.database.datasource

import com.buildinglink.dictionary.data.database.model.DatabasePhonetic

interface LocalDataSource {

    suspend fun getDefinition(query: String): List<DatabasePhonetic>

    suspend fun saveDefinition(query: String, phonetics: List<DatabasePhonetic>)
}
