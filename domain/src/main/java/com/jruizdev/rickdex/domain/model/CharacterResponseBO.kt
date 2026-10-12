package com.jruizdev.rickdex.domain.model

data class CharacterResponseBO (
    val info: InfoBO,
    val characters: List<CharacterBO>
)