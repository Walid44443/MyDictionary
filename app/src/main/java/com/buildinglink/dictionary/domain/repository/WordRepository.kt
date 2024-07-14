package com.buildinglink.dictionary.domain.repository

import com.buildinglink.dictionary.domain.data.model.Word
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    suspend fun searchDefinition(query: String): Flow<Result<Word>>
}
