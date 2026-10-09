package com.jruizdev.rickdex.data.datasource

import com.jruizdev.rickdex.data.database.dao.CharacterDao
import com.jruizdev.rickdex.data.mapper.mapToBO
import com.jruizdev.rickdex.data.mapper.mapToEntity
import com.jruizdev.rickdex.domain.model.CharacterBO
import com.jruizdev.rickdex.domain.model.CharacterResponseBO
import javax.inject.Inject

class CharacterDatasourceImpl @Inject constructor(
    private val api: CharactersApi,
    private val dao: CharacterDao
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
        val entities = charactersResponse.results.map { it.mapToEntity() }
        dao.insertCharacters(entities)
        return charactersResponse.mapToBO()
    }

    override suspend fun getCharacter(id: Int): CharacterBO {
        return try {
            val characterDto = api.getCharacter(id)
            val entity = characterDto.mapToEntity()
            dao.insertCharacter(entity)
            characterDto.mapToBO()
        } catch (e: Exception) {
            val cachedEntity = dao.getCharacterById(id)
            cachedEntity?.mapToBO() ?: throw e
        }
    }
}
