package com.jruizdev.rickdex.ui.characters.mapper

import com.jruizdev.rickdex.R
import com.jruizdev.rickdex.domain.model.CharacterBO
import com.jruizdev.rickdex.domain.model.GenderBO
import com.jruizdev.rickdex.domain.model.StatusBO
import com.jruizdev.rickdex.ui.character_detail.model.CharacterDetailVO
import com.jruizdev.rickdex.ui.character_detail.model.GenderVO
import com.jruizdev.rickdex.ui.character_detail.model.StatusVO
import com.jruizdev.rickdex.ui.characters.CharacterFieldVO
import com.jruizdev.rickdex.ui.characters.CharacterVO

fun CharacterBO.toVO() = CharacterVO(
    id = id, fields = listOf(
        CharacterFieldVO(R.string.name, name),
        CharacterFieldVO(R.string.status, status.name),
        CharacterFieldVO(R.string.species, species),
    ), image = image
)

fun CharacterBO.toCharacterDetailVO() = CharacterDetailVO(
    id = id,
    name = name,
    status = status.toVO(),
    species = species,
    origin = origin.name,
    location = location.name,
    gender = gender.toVO(),
    image = image,
    episodes = episode.joinToString()
)

fun StatusBO.toVO() = when (this) {
    StatusBO.ALIVE -> StatusVO.ALIVE
    StatusBO.DEAD -> StatusVO.DEAD
    StatusBO.UNKNOWN -> StatusVO.UNKNOWN
}

fun GenderBO.toVO() = when (this) {
    GenderBO.FEMALE -> GenderVO.FEMALE
    GenderBO.MALE -> GenderVO.MALE
    GenderBO.GENDERLESS -> GenderVO.GENDERLESS
    GenderBO.UNKNOWN -> GenderVO.UNKNOWN
}