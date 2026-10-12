package com.jruizdev.rickdex.ui.character_detail

sealed interface CharacterDetailIntent {
    data class GetCharacter(val id: Int) : CharacterDetailIntent
}
