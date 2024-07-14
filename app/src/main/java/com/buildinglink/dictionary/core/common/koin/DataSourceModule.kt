package com.buildinglink.dictionary.core.common.koin

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.buildinglink.dictionary.core.model.CoroutinesDispatchers
import com.buildinglink.dictionary.data.database.WordDatabase
import com.buildinglink.dictionary.data.database.dao.PhoneticDao
import com.buildinglink.dictionary.data.database.datasource.LocalDataSource
import com.buildinglink.dictionary.data.database.datasource.LocalDataSourceImpl
import com.buildinglink.dictionary.data.network.datasource.remote.RemoteDataSource
import com.buildinglink.dictionary.data.network.datasource.remote.RemoteDataSourceImpl
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val dataSourceModule = module {

    factory<OkHttpClient>() {
        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    factory<LocalDataSource> {
        LocalDataSourceImpl(
            phoneticDao = get(),
            ioDispatcher = get(named(CoroutinesDispatchers.IO.value))
        )
    }

    factory<RemoteDataSource> {
        RemoteDataSourceImpl(
            okHttpClient = get(),
            urlConfigurationService = get()
        )
    }



    single<WordDatabase> {
        provideDataBase(androidApplication())
    }

    single {
        providePhoneticDao(get())
    }
}

fun provideDataBase(application: Application): WordDatabase =
    Room.databaseBuilder(
        application,
        WordDatabase::class.java,
        "table_post"
    ).fallbackToDestructiveMigration().build()

fun providePhoneticDao(wordDatabase: WordDatabase) = wordDatabase.phoneticDao()