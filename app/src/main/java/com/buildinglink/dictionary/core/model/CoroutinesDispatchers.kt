package com.buildinglink.dictionary.core.model

enum class CoroutinesDispatchers(val value: String) {
    IO("IODispatcher"),
    Default("DefaultDispatcher"),
    Main("MainDispatcher"),
    Unconfined("UnconfinedDispatcher")
}