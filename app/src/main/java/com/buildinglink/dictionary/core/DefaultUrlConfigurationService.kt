package com.buildinglink.dictionary.core

import android.content.Context
import com.buildinglink.dictionary.R
import com.buildinglink.dictionary.core.configuration.UrlConfigurationService

class DefaultUrlConfigurationService(private val context: Context) : UrlConfigurationService {
    override val dictionaryBaseUrl: String
        get() = context.resources.getString(R.string.dictionary_base_url)
    override val dictionaryLanguageCode: String
        get() = context.resources.getString(R.string.dictionary_language_code)
}
