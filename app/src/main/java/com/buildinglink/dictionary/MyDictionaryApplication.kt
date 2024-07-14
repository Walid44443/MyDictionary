package com.buildinglink.dictionary

import android.app.Application
import com.buildinglink.dictionary.core.common.koin.appModule
import com.buildinglink.dictionary.core.common.koin.coroutineDispatchersModule
import com.buildinglink.dictionary.core.common.koin.dataSourceModule
import com.buildinglink.dictionary.core.common.koin.repositoryModule
import com.buildinglink.dictionary.core.common.koin.useCaseModule
import com.buildinglink.dictionary.core.common.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class MyDictionaryApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyDictionaryApplication)
            modules(
                appModule,
                dataSourceModule,
                repositoryModule,
                useCaseModule,
                viewModelModule,
                coroutineDispatchersModule
            )
        }
    }
}