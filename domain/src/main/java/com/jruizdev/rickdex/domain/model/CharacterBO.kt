package com.jruizdev.rickdex.domain.model

data class CharacterBO(
    val id: Int = 0,
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val type: String = "",
    val gender: String = "",
    val origin: OriginBO = OriginBO(),
    val image: String = "",
    val episode: List<String> = emptyList(),
)

data class OriginBO(
    val name: String = "",
    val url: String = "",
)