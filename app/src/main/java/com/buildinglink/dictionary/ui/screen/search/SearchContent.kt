package com.buildinglink.dictionary.ui.screen.search

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.Listener
import androidx.media3.exoplayer.ExoPlayer
import com.buildinglink.dictionary.R
import com.buildinglink.dictionary.domain.data.model.Phonetic

@Composable
fun SearchContent(
    modifier: Modifier = Modifier,
    searchQuery: String,
    isLoading: Boolean,
    isNotFoundItems: Boolean,
    isNetworkError: Boolean,
    errorMessage: String,
    definition: String,
    phonetics: List<Phonetic>,
    onSearchQueryChange: (query: String) -> Unit = {},
    searchDefinition: () -> Unit = {},
) {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { onSearchQueryChange(it) },
            label = { Text(text = stringResource(id = R.string.search_screen_enter_word)) },
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { searchDefinition() }) {
            Text(text = stringResource(id = R.string.search_screen_search_button))
        }
        AnimatedVisibility(
            visible = isLoading
        ) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        AnimatedVisibility(visible = !isLoading) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = definition)
                if (phonetics.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = stringResource(id = R.string.search_screen_pronunciations))
                    LazyColumn {
                        items(phonetics) { phonetic ->
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(text = phonetic.text)
                                Spacer(modifier = Modifier.weight(1f))
                                if (phonetic.audio.isNotEmpty()) {
                                    AudioPlayer(audioUrl = phonetic.audio)
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                        }

                    }
                }
            }
        }

        AnimatedVisibility(visible = errorMessage.isNotEmpty()) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
            ) {
                // TODO visualise icon or image based on type of error
                Text(text = errorMessage)
            }
        }
    }
}


@Composable
fun AudioPlayer(modifier: Modifier = Modifier, audioUrl: String) {
    var player by remember { mutableStateOf<ExoPlayer?>(null) }
    var isPlayingAudio by remember { mutableStateOf(false) }


    fun preparePlayer(context: Context) {
        val exoPlayer = ExoPlayer.Builder(context).build()
        exoPlayer.setMediaItem(MediaItem.fromUri(audioUrl))
        exoPlayer.prepare()
        player = exoPlayer
    }

    val playPause = fun() {
        if (player != null) {
            if (isPlayingAudio.not()) {
                player?.seekTo(0)
                player?.play()
            } else {
                player?.pause()
            }
        }
    }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        preparePlayer(context)
        player?.addListener(object : Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                isPlayingAudio = isPlaying
            }

        })
    }

    DisposableEffect(key1 = true) {
        onDispose {
            player?.release()
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = playPause) {
            Icon(
                if (isPlayingAudio) Icons.Filled.Clear else Icons.Filled.PlayArrow,
                contentDescription = stringResource(
                    id = if (isPlayingAudio) {
                        R.string.search_screen_play
                    } else {
                        R.string.search_screen_pause
                    }
                )
            )
        }
    }
}
