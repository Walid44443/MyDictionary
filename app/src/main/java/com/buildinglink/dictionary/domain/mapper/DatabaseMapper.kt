package com.buildinglink.dictionary.domain.mapper

import com.buildinglink.dictionary.data.database.model.DatabasePhonetic
import com.buildinglink.dictionary.domain.data.model.Phonetic
import com.buildinglink.dictionary.domain.data.model.Word

fun List<DatabasePhonetic>.toDomainWord(): Word {
    return Word(
        value = this.firstOrNull()?.word ?: "",
        phoneticList = this.map { it.toDomainPhonetic() }
    )
}

fun DatabasePhonetic.toDomainPhonetic(): Phonetic =
    Phonetic(text = this.text, audio = this.audio)