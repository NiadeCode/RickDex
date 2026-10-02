package com.jruizdev.rickdex.ui.characters

data class CharactersUiState(
    val isLoading: Boolean = false,
    val characters: List<CharacterVO> = emptyList(),
    val error: String? = null,
    val page: Int = 1,
    val hasMore: Boolean = true
)

data class CharacterVO(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: String,
    val image: String
)