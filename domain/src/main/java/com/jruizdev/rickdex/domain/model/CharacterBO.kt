package com.jruizdev.rickdex.domain.model

data class CharacterBO(
    val id: Int = 0,
    val name: String = "",
    val status: StatusBO = StatusBO.UNKNOWN,
    val species: String = "",
    val type: String = "",
    val gender: GenderBO = GenderBO.UNKNOWN,
    val origin: OriginBO = OriginBO(),
    val image: String = "",
    val episode: List<String> = emptyList(),
)

data class OriginBO(
    val name: String = "",
    val url: String = "",
)

enum class GenderBO {
    FEMALE, MALE, GENDERLESS, UNKNOWN,
}

enum class StatusBO {
    ALIVE, DEAD, UNKNOWN,
}