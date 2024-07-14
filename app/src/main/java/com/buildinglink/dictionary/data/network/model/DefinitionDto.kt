package com.buildinglink.dictionary.data.network.model


import com.google.gson.annotations.SerializedName

data class DefinitionDto(
    @SerializedName("meanings")
    val meanings: List<MeaningDto?>?,
    @SerializedName("origin")
    val origin: String?,
    @SerializedName("phonetic")
    val phonetic: String?,
    @SerializedName("phonetics")
    val phonetics: List<PhoneticDto?>?,
    @SerializedName("word")
    val word: String?
)
