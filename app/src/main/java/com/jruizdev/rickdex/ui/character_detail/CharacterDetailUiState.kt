package com.jruizdev.rickdex.ui.character_detail

import com.jruizdev.rickdex.ui.character_detail.model.CharacterDetailVO

sealed interface CharacterDetailUiState {
    data object Loading: CharacterDetailUiState
    data object Error: CharacterDetailUiState
    data class Success(val character: CharacterDetailVO): CharacterDetailUiState
}
