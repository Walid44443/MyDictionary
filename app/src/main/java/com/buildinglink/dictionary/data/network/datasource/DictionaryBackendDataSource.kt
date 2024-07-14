package com.buildinglink.dictionary.data.network.datasource

import com.buildinglink.dictionary.core.exception.backend.BadRequestException
import com.buildinglink.dictionary.core.exception.backend.NotFoundException
import com.buildinglink.dictionary.core.exception.backend.ServerErrorException
import com.buildinglink.dictionary.core.exception.backend.UnAuthorizedException
import com.buildinglink.dictionary.core.exception.backend.UnProcessableException
import com.buildinglink.dictionary.core.exception.backend.UnknownBackendException
import com.buildinglink.dictionary.core.response.BaseResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class DictionaryBackendDataSource(url: String, httpClient: OkHttpClient) {

    val gson: Gson = GsonBuilder().create()

    protected val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(url)
        .client(httpClient)
        .build()

    protected fun <T : BaseResponse> handleResponse(response: Response<T>): T {
        if (response.isSuccessful) {
            return requireNotNull(response.body())
        }
        throw generateException(response)
    }

    //region Private methods
    private fun <T> generateException(response: Response<T>): Exception {
        return when (response.code()) {
            400 ->
                BadRequestException(message = response.message())

            401 ->
                UnAuthorizedException(message = response.message())

            404 ->
                NotFoundException(message = response.message())

            422 ->
                UnProcessableException(message = response.message())

            in 500..504 ->
                ServerErrorException(message = response.message())

            else ->
                UnknownBackendException(message = response.message())
        }
    }
    //endregion
}
