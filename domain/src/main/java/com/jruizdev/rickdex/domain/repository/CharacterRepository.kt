package com.jruizdev.rickdex.domain.repository

import com.jruizdev.rickdex.domain.model.CharacterBO
import com.jruizdev.rickdex.domain.model.CharacterResponseBO

interface CharacterRepository {
    suspend fun getCharacters(page: Int): CharacterResponseBO

    suspend fun getCharacter(id: Int): CharacterBO
}
