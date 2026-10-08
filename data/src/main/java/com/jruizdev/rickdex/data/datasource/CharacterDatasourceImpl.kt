package com.jruizdev.rickdex.data.datasource

import com.jruizdev.rickdex.data.mapper.mapToBO
import com.jruizdev.rickdex.domain.model.CharacterBO
import com.jruizdev.rickdex.domain.model.CharacterResponseBO
import javax.inject.Inject

class CharacterDatasourceImpl @Inject constructor(
    private val api: CharactersApi
) : CharacterDatasource {

    override suspend fun getCharacters(
        page: Int,
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): CharacterResponseBO {
        if (page <= 0) {
            throw IllegalArgumentException("Page must be greater than 0")
        }
        val charactersResponse = api.getCharacters(page, name, status, species, type, gender)
        return charactersResponse.mapToBO()
    }

    override suspend fun getCharacter(id: Int): CharacterBO {
        return api.getCharacter(id).mapToBO()
    }
}
