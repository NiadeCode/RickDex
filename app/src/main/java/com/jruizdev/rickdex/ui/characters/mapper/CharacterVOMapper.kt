package com.jruizdev.rickdex.ui.characters.mapper

import com.jruizdev.rickdex.domain.model.CharacterBO
import com.jruizdev.rickdex.ui.characters.CharacterVO

fun CharacterBO.toVO() = CharacterVO(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin.name,
    image = image
)