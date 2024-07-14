package com.buildinglink.dictionary.data.network.model


import com.google.gson.annotations.SerializedName

data class PhoneticDto(
    @SerializedName("audio")
    val audio: String?,
    @SerializedName("text")
    val text: String?
)
