package com.jruizdev.rickdex.data.datasource

import com.jruizdev.rickdex.domain.model.CharacterBO
import com.jruizdev.rickdex.domain.model.CharacterResponseBO

interface CharacterDatasource {

    suspend fun getCharacters(
        page: Int,
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): CharacterResponseBO

    suspend fun getCharacter(id: Int): CharacterBO

}