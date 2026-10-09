package com.jruizdev.rickdex.domain.repository

import androidx.paging.PagingData
import com.jruizdev.rickdex.domain.model.CharacterBO
import com.jruizdev.rickdex.domain.model.CharacterResponseBO
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacters(page: Int): CharacterResponseBO

    fun getCharactersStream(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): Flow<PagingData<CharacterBO>>

    suspend fun getCharacter(id: Int): CharacterBO
}
