package com.jruizdev.rickdex.domain.repository

import com.jruizdev.rickdex.domain.model.CharacterBO
interface CharacterRepository {
    suspend fun getCharacters(): List<CharacterBO>

    suspend fun getCharacter(id: Int): CharacterBO
}