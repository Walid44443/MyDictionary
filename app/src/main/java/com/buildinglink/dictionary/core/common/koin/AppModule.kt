package com.buildinglink.dictionary.core.common.koin

import com.buildinglink.dictionary.core.DefaultUrlConfigurationService
import com.buildinglink.dictionary.core.configuration.UrlConfigurationService
import org.koin.dsl.module

val appModule = module {
    single<UrlConfigurationService> { DefaultUrlConfigurationService(get()) }
}