package com.buildinglink.dictionary.domain.data.model

data class Word(
    val value: String,
    val phoneticList: List<Phonetic>
)
