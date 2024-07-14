package com.buildinglink.dictionary.core.common.koin

import com.buildinglink.dictionary.domain.repository.WordRepository
import com.buildinglink.dictionary.domain.repository.WordRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<WordRepository> {
        WordRepositoryImpl(
            localDataSource = get(),
            remoteDataSource = get(),
        )
    }
}