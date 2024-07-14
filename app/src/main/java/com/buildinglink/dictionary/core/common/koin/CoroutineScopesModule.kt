package com.buildinglink.dictionary.core.common.koin

import com.buildinglink.dictionary.core.model.CoroutinesDispatchers
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module


val coroutineDispatchersModule = module {

    single(named(CoroutinesDispatchers.IO.value)) {
        Dispatchers.IO
    }

    single(named(CoroutinesDispatchers.Default.value)) {
        Dispatchers.Default
    }

    single(named(CoroutinesDispatchers.Main.value)) {
        Dispatchers.Main
    }

    single(named(CoroutinesDispatchers.Unconfined.value)) {
        Dispatchers.Unconfined
    }

}