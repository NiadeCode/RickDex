package com.jruizdev.rickdex.ui.characters.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jruizdev.rickdex.ui.characters.CharactersUiState
import com.jruizdev.rickdex.ui.composables.ErrorView
import com.jruizdev.rickdex.ui.composables.LoadingView
import com.jruizdev.rickdex.ui.theme.MultiversePink
import com.jruizdev.rickdex.ui.theme.RickCyanLight

@Composable
fun CharactersContent(
    state: CharactersUiState = CharactersUiState(),
    onCharacterClick: (Int) -> Unit
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            CharactersTopBar()
        }
    ) { innerPadding ->

        if (state.error != null) {
            ErrorView()
        }

        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(state.characters.size) { index ->
                val character = state.characters[index]

                CharacterListRow(character = character, onCharacterClick = onCharacterClick)
                if (index < state.characters.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(start = 64.dp),
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.size(2.dp))
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 32.dp),
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )

                }
            }
        }

        if (state.isLoading) {
            LoadingView()
        }
    }
}

