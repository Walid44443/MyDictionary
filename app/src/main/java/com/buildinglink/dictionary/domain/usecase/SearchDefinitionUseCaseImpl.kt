package com.buildinglink.dictionary.domain.usecase

import com.buildinglink.dictionary.domain.repository.WordRepository
import kotlinx.coroutines.flow.map

class SearchDefinitionUseCaseImpl(
    private val repository: WordRepository
) : SearchDefinitionUseCase {
    override suspend fun invoke(query: String) =
        repository.searchDefinition(query)
}