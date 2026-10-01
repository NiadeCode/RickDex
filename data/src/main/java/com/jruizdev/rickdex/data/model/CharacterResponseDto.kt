package com.jruizdev.rickdex.data.model

data class CharacterResponseDto(
    val info: InfoDto,
    val results: List<CharacterDto>
)
