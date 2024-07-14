package com.buildinglink.dictionary.data.network.datasource.remote


import com.buildinglink.dictionary.core.configuration.UrlConfigurationService
import com.buildinglink.dictionary.data.network.datasource.DictionaryBackendDataSource
import com.buildinglink.dictionary.data.network.model.response.DefinitionResponse
import com.buildinglink.dictionary.data.network.service.DictionaryService
import okhttp3.OkHttpClient

class RemoteDataSourceImpl(
    okHttpClient: OkHttpClient,
    private val urlConfigurationService: UrlConfigurationService
) : DictionaryBackendDataSource(urlConfigurationService.dictionaryBaseUrl, okHttpClient),
    RemoteDataSource {

    private val backendService by lazy { retrofit.create(DictionaryService::class.java) }

    override suspend fun getDefinition(query: String): DefinitionResponse {
        return handleResponse(
            backendService.getDefinition(
                languageCode = urlConfigurationService.dictionaryLanguageCode,
                word = query
            )
        )
    }
}
