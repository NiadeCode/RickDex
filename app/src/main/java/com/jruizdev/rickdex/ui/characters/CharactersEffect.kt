package com.jruizdev.rickdex.ui.characters

sealed interface CharactersEffect {
    data class NavigateToDetail(val characterId: Int) : CharactersEffect
}