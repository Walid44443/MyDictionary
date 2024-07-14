package com.buildinglink.dictionary.domain.mapper

import com.buildinglink.dictionary.data.database.model.DatabasePhonetic
import com.buildinglink.dictionary.data.network.model.PhoneticDto


fun PhoneticDto.toDatabasePhonetic(query: String): DatabasePhonetic {
    return DatabasePhonetic(
        word = query,
        audio = this.audio ?: "", // Handle potential null audio URL
        text = this.text ?: ""  // Handle potential null text
    )
}