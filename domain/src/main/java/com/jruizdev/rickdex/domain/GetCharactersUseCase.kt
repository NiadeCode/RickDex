package com.jruizdev.rickdex.domain

import com.jruizdev.rickdex.domain.model.CharacterResponseBO
import com.jruizdev.rickdex.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(page: Int = 1): Result<CharacterResponseBO> {
        return runCatching {
            characterRepository.getCharacters(page)
        }
    }
}
