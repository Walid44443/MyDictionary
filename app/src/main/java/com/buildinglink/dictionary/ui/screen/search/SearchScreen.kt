package com.buildinglink.dictionary.ui.screen.search

import androidx.compose.foundation.layout.*


import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView


@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    SearchContent(
        modifier = Modifier
            .safeContentPadding()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        searchQuery = viewModel.searchQuery.collectAsState("").value,
        isLoading = viewModel.isLoading.collectAsState(false).value,
        isNotFoundItems = viewModel.isNotFoundItems.collectAsState(false).value,
        isNetworkError = viewModel.isNetworkError.collectAsState(false).value,
        errorMessage = viewModel.errorMessage.collectAsState("").value,
        definition = viewModel.definition.collectAsState("").value,
        phonetics = viewModel.phonetics.collectAsState(emptyList()).value,
        searchDefinition = viewModel::searchDefinition,
        onSearchQueryChange = viewModel::onSearchQueryChange,
    )
}
