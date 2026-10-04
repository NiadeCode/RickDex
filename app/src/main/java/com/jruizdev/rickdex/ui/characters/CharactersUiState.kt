package com.jruizdev.rickdex.ui.characters

import androidx.annotation.StringRes

data class CharactersUiState(
    val isLoading: Boolean = false,
    val characters: List<CharacterVO> = emptyList(),
    val error: String? = null,
    val page: Int = 1,
    val hasMore: Boolean = true
)

data class CharacterVO(
    val id: Int, val fields: List<CharacterFieldVO>, val image: String
)

data class CharacterFieldVO(
    @StringRes val title: Int, val value: String
)