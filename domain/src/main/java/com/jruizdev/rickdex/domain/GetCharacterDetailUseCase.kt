package com.jruizdev.rickdex.domain

import com.jruizdev.rickdex.domain.model.CharacterBO
import com.jruizdev.rickdex.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterDetailUseCase@Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(id: Int): CharacterBO = characterRepository.getCharacter(id)
}
