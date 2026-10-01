package com.jruizdev.rickdex.data.repository

import com.jruizdev.rickdex.data.datasource.CharacterDatasource
import com.jruizdev.rickdex.domain.model.CharacterBO
import com.jruizdev.rickdex.domain.model.CharacterResponseBO
import com.jruizdev.rickdex.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val datasource: CharacterDatasource
) : CharacterRepository {
    override suspend fun getCharacters(page: Int): CharacterResponseBO {
        return datasource.getCharacters(page)
    }

    override suspend fun getCharacter(id: Int): CharacterBO {
        return datasource.getCharacter(id)
    }
}
