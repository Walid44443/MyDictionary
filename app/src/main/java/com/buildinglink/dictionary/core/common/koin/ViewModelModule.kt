package com.buildinglink.dictionary.core.common.koin

import com.buildinglink.dictionary.ui.screen.search.SearchViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        SearchViewModel(
            searchDefinitionUseCase = get(),
            context = androidApplication()
        )
    }
}