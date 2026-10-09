package com.jruizdev.rickdex.ui.characters

sealed interface CharactersIntent {
    data class NavigateToCharacterDetails(val characterId: Int) : CharactersIntent
}