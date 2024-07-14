package com.buildinglink.dictionary.data.database.datasource

import com.buildinglink.dictionary.core.model.CoroutinesDispatchers
import com.buildinglink.dictionary.data.database.dao.PhoneticDao
import com.buildinglink.dictionary.data.database.model.DatabasePhonetic
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LocalDataSourceImpl(
    private val phoneticDao: PhoneticDao,
    private val ioDispatcher: CoroutineDispatcher
) : LocalDataSource {

    override suspend fun getDefinition(query: String): List<DatabasePhonetic> =
        withContext(ioDispatcher) {
            return@withContext phoneticDao.getDefinition(query)
        }

    override suspend fun saveDefinition(query: String, phonetics: List<DatabasePhonetic>) =
        withContext(ioDispatcher) {
            phoneticDao.deleteDefinition(query)
            phoneticDao.insertPhonetics(phonetics)
        }
}