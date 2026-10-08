package com.jruizdev.rickdex.ui.characters.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.jruizdev.rickdex.domain.exception.RateLimitException
import com.jruizdev.rickdex.ui.characters.CharacterVO
import com.jruizdev.rickdex.ui.composables.ErrorView
import com.jruizdev.rickdex.ui.composables.ListDecorator
import com.jruizdev.rickdex.ui.composables.LoadingView

@Composable
fun CharactersContent(
    characters: LazyPagingItems<CharacterVO>,
    onCharacterClick: (Int) -> Unit,
    onQueryChange: (String, Boolean) -> Unit
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            CharactersTopBar(onQueryChange)
        }) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(
                count = characters.itemCount,
                key = { index -> characters[index]?.id ?: index }) { index ->
                val character = characters[index]
                if (character != null) {
                    CharacterListRow(character = character, onCharacterClick = onCharacterClick)
                    if (index < characters.itemCount - 1) {
                        ListDecorator()
                    }
                }
            }

            // Handle initial load state (Refresh)
            when (val refreshState = characters.loadState.refresh) {
                is LoadState.Loading -> {
                    handleLoadStateLoading()
                }

                is LoadState.Error -> {
                    handleLoadStateError(
                        error = refreshState.error,
                        onRetry = { characters.retry() }
                    )
                }

                else -> {}
            }

            when (val appendState = characters.loadState.append) {
                is LoadState.Loading -> {
                    handleLoadStateLoading()
                }

                is LoadState.Error -> {
                    handleLoadStateError(
                        error = appendState.error,
                        onRetry = { characters.retry() }
                    )
                }

                else -> {}
            }
        }
    }
}

private fun LazyListScope.handleLoadStateLoading() {
    item {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), contentAlignment = Alignment.Center
        ) {
            LoadingView(Modifier.fillMaxWidth())
        }
    }
}

private fun LazyListScope.handleLoadStateError(
    error: Throwable, onRetry: () -> Unit
) {
    val errorMessage = if (error is RateLimitException) {
        "¡Vas demasiado rápido Morty! \nEspera un momento y toca el pepino."
    } else {
        "¡Me convertí en un \n error, Morty!"
    }

    item {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable(onClick = onRetry),
            contentAlignment = Alignment.Center
        ) {
            ErrorView(message = errorMessage)
        }
    }
}
