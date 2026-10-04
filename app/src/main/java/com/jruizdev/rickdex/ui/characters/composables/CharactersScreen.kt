package com.jruizdev.rickdex.ui.characters.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.jruizdev.rickdex.ui.characters.CharactersEffect
import com.jruizdev.rickdex.ui.characters.CharactersIntent
import com.jruizdev.rickdex.ui.characters.CharactersViewModel

@Composable
fun CharactersScreen(
    viewModel: CharactersViewModel = hiltViewModel<CharactersViewModel>(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

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
        state = state, onCharacterClick = { characterId ->
            // El usuario hace clic -> Enviamos el intent
            viewModel.sendIntent(CharactersIntent.NavigateToCharacterDetails(characterId))
        })
}




