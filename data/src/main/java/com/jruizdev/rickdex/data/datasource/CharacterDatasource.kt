package com.jruizdev.rickdex.data.datasource

import com.jruizdev.rickdex.domain.model.CharacterBO
import com.jruizdev.rickdex.domain.model.CharacterResponseBO

interface CharacterDatasource {

    suspend fun getCharacters(
        page: Int,
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): CharacterResponseBO

    suspend fun getCharacter(id: Int): CharacterBO

}