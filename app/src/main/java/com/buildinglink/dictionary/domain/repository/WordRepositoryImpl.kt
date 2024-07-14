package com.buildinglink.dictionary.domain.repository


import com.buildinglink.dictionary.data.database.datasource.LocalDataSource
import com.buildinglink.dictionary.data.network.datasource.remote.RemoteDataSource
import com.buildinglink.dictionary.data.network.model.PhoneticDto
import com.buildinglink.dictionary.domain.data.model.Word
import com.buildinglink.dictionary.domain.mapper.toDatabasePhonetic
import com.buildinglink.dictionary.domain.mapper.toDomainWord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WordRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : WordRepository {

    override suspend fun searchDefinition(query: String): Flow<Result<Word>> =
        flow {
            runCatching {
                val cachedDataResult = runCatching { localDataSource.getDefinition(query) }
                val cachedData = cachedDataResult.getOrThrow()
                if (cachedData.isNotEmpty()) {
                    val word = cachedData.toDomainWord()
                    emit(Result.success(word))
                } else {
                    val networkDataResult =
                        kotlin.runCatching { remoteDataSource.getDefinition(query) }
                    val networkData = networkDataResult.getOrThrow()
                    val phoneticsList =
                        networkData.firstOrNull()?.phonetics?.filterNotNull() ?: emptyList()
                    if (phoneticsList.isNotEmpty()) {
                        cacheNetworkPhonetics(query, phoneticsList)
                        emit(Result.success(localDataSource.getDefinition(query).toDomainWord()))
                    }
                }
            }.onFailure { action->
                emit(Result.failure(action))
            }
        }

    private suspend fun cacheNetworkPhonetics(query: String, phoneticList: List<PhoneticDto>?) {
        localDataSource.saveDefinition(
            query = query,
            phonetics = phoneticList?.map { it.toDatabasePhonetic(query) } ?: emptyList()
        )
    }
}
