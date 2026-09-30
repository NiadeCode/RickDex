package com.jruizdev.rickdex.data.repository

import com.jruizdev.rickdex.domain.model.CharacterBO
import com.jruizdev.rickdex.domain.repository.CharacterRepository

class CharacterRepositoryImpl : CharacterRepository {
    override suspend fun getCharacters(): List<CharacterBO> {
        TODO("Not yet implemented")
    }

    override suspend fun getCharacter(id: Int): CharacterBO {
        TODO("Not yet implemented")
    }
}