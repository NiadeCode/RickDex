package com.jruizdev.rickdex.data.mapper

import com.jruizdev.rickdex.data.model.CharacterDto
import com.jruizdev.rickdex.data.model.CharacterResponseDto
import com.jruizdev.rickdex.data.model.InfoDto
import com.jruizdev.rickdex.data.model.LocationDto
import com.jruizdev.rickdex.domain.model.CharacterBO
import com.jruizdev.rickdex.domain.model.CharacterResponseBO
import com.jruizdev.rickdex.domain.model.GenderBO
import com.jruizdev.rickdex.domain.model.InfoBO
import com.jruizdev.rickdex.domain.model.OriginBO
import com.jruizdev.rickdex.domain.model.StatusBO
import java.net.URI

fun CharacterResponseDto.mapToBO(): CharacterResponseBO {
    return CharacterResponseBO(
        info = info.mapToBO(), characters = results.map { it.mapToBO() })
}

fun InfoDto.mapToBO(): InfoBO {
    val page = next?.let { url ->
        try {
            URI(url).query
                ?.split("&")
                ?.firstOrNull { it.startsWith("page=") }
                ?.substringAfter("page=")
                ?.toIntOrNull()
        } catch (e: Exception) {
            null
        }
    }
    return InfoBO(
        pages = pages,
        next = page,
    )
}

fun CharacterDto.mapToBO(): CharacterBO {
    return CharacterBO(
        id = id,
        name = name,
        status = status.toStatus(),
        species = species,
        type = type,
        gender = gender.toGender(),
        origin = origin.mapToBO(),
        image = image,
        episode = episode,
    )
}

private fun String.toStatus(): StatusBO = when (this.lowercase()) {
    "alive" -> StatusBO.ALIVE
    "dead" -> StatusBO.DEAD
    else -> StatusBO.UNKNOWN // Valor por defecto si la API cambia o falla
}

private fun String.toGender(): GenderBO = when (this.lowercase()) {
    "female" -> GenderBO.FEMALE
    "dead" -> GenderBO.MALE
    "genderless" -> GenderBO.GENDERLESS
    else -> GenderBO.UNKNOWN // Valor por defecto si la API cambia o falla
}

fun LocationDto.mapToBO(): OriginBO {
    return OriginBO(
        name = name,
        url = url,
    )
}
