package com.jruizdev.rickdex.ui.characters.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.jruizdev.rickdex.ui.characters.CharactersEffect
import com.jruizdev.rickdex.ui.characters.CharactersIntent
import com.jruizdev.rickdex.ui.characters.CharactersViewModel

@Composable
fun CharactersScreen(
    viewModel: CharactersViewModel = hiltViewModel<CharactersViewModel>(),
    navController: NavController
) {
    val characters = viewModel.characters.collectAsLazyPagingItems()

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CharactersEffect.NavigateToDetail -> {
                    navController.navigate("character_detail/${effect.characterId}")
                }
            }
        }
    }

    CharactersContent(
        characters = characters,
        onCharacterClick = { characterId ->
            viewModel.sendIntent(CharactersIntent.NavigateToCharacterDetails(characterId))
        },
        onQueryChange = { query, force ->
            viewModel.sendIntent(CharactersIntent.UpdateQuery(query, force))
        },
    )
}
