package com.buildinglink.dictionary.data.network.model


import com.google.gson.annotations.SerializedName

data class MeaningDto(
    @SerializedName("definitions")
    val definitions: List<DefinitionDto?>?,
    @SerializedName("partOfSpeech")
    val partOfSpeech: String?
)
