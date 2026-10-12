package com.jruizdev.rickdex.domain

import androidx.paging.PagingData
import com.jruizdev.rickdex.domain.model.CharacterBO
import com.jruizdev.rickdex.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    operator fun invoke(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): Flow<PagingData<CharacterBO>> {
        return characterRepository.getCharactersStream(
            name = name, status = status, species = species, type = type, gender = gender
        )
    }
}
