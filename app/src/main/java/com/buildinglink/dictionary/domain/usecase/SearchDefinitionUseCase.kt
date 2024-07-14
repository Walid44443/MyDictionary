package com.buildinglink.dictionary.domain.usecase

import com.buildinglink.dictionary.domain.data.model.Word
import kotlinx.coroutines.flow.Flow

interface SearchDefinitionUseCase {
    suspend operator fun invoke(query: String): Flow<Result<Word>>
}