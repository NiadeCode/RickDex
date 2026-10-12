package com.jruizdev.rickdex.ui.character_detail.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.jruizdev.rickdex.ui.character_detail.CharacterDetailUiState
import com.jruizdev.rickdex.ui.character_detail.CharacterDetailViewModel
import com.jruizdev.rickdex.ui.composables.ErrorView
import com.jruizdev.rickdex.ui.composables.LoadingView

@Composable
fun CharacterDetailScreen(
    viewModel: CharacterDetailViewModel = hiltViewModel<CharacterDetailViewModel>(),
    navController: NavController
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            CharacterDetailTopBar(
                modifier = Modifier,
                navController = navController,
            )
        }) { innerPadding ->
        when (val sureState = state) {
            is CharacterDetailUiState.Loading -> {
                LoadingView(
                    Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                )
            }

            is CharacterDetailUiState.Error -> {
                ErrorView(
                    Modifier.padding(innerPadding)
                )
            }

            is CharacterDetailUiState.Success -> {
                CharacterDetailContent(
                    modifier = Modifier.padding(start = 16.dp, top = innerPadding.calculateTopPadding(), end = 16.dp, bottom = innerPadding.calculateBottomPadding()),
                    character = sureState.character,
                )
            }
        }

    }


}
