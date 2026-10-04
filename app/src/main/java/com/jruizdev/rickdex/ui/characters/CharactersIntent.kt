package com.jruizdev.rickdex.ui.characters

sealed interface CharactersIntent {
    object LoadCharacters : CharactersIntent
    data class NavigateToCharacterDetails(val characterId: Int) : CharactersIntent
}