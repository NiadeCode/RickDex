package com.jruizdev.rickdex.ui.characters.mapper

import com.jruizdev.rickdex.R
import com.jruizdev.rickdex.domain.model.CharacterBO
import com.jruizdev.rickdex.ui.characters.CharacterFieldVO
import com.jruizdev.rickdex.ui.characters.CharacterVO

fun CharacterBO.toVO() = CharacterVO(
    id = id,
    fields = listOf(
        CharacterFieldVO(R.string.name, name),
        CharacterFieldVO(R.string.status, status),
        CharacterFieldVO(R.string.species, species),
    ),
    image = image
)