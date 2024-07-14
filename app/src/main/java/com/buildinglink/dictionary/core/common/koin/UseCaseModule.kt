package com.buildinglink.dictionary.core.common.koin

import com.buildinglink.dictionary.domain.usecase.SearchDefinitionUseCase
import com.buildinglink.dictionary.domain.usecase.SearchDefinitionUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    factory<SearchDefinitionUseCase> {
        SearchDefinitionUseCaseImpl(
            repository = get()
        )
    }
}