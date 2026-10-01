package com.jruizdev.rickdex.domain

import com.jruizdev.rickdex.domain.model.CharacterBO
import com.jruizdev.rickdex.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(page: Int = 1): List<CharacterBO> {
        return characterRepository.getCharacters()
    }
}
