package com.jruizdev.rickdex.ui.characters

sealed interface CharactersIntent {
    data class NavigateToCharacterDetails(val characterId: Int) : CharactersIntent
    data class UpdateQuery(val query: String, val force: Boolean) : CharactersIntent
}
