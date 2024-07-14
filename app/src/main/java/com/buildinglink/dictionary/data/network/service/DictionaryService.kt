package com.buildinglink.dictionary.data.network.service

import com.buildinglink.dictionary.data.network.model.response.DefinitionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DictionaryService {

    @GET("/api/v2/entries/{language_code}/{word}")
    suspend fun getDefinition(
        @Path("language_code") languageCode: String,
        @Path("word") word: String
    ): Response<DefinitionResponse>
}
