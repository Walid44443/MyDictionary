package com.buildinglink.dictionary.data.network.datasource.remote

import com.buildinglink.dictionary.data.network.model.response.DefinitionResponse


interface RemoteDataSource {

    suspend fun getDefinition(query: String): DefinitionResponse  // Replace with your API response model
}
