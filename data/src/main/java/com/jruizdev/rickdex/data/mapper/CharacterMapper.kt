package com.jruizdev.rickdex.data.mapper

import com.jruizdev.rickdex.data.database.entity.CharacterEntity
import com.jruizdev.rickdex.data.database.entity.LocationEntity
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

fun CharacterDto.mapToEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin.mapToEntity(),
        location = location.mapToEntity(),
        image = image,
        episodes = episode.joinToString(","),
        url = url,
        created = created
    )
}

fun CharacterEntity.mapToBO(): CharacterBO {
    return CharacterBO(
        id = id,
        name = name,
        status = status.toStatus(),
        species = species,
        type = type,
        gender = gender.toGender(),
        origin = OriginBO(name = origin.name, url = origin.url),
        image = image,
        episode = if (episodes.isBlank()) emptyList() else episodes.split(","),
    )
}

fun LocationDto.mapToEntity(): LocationEntity {
    return LocationEntity(
        name = name,
        url = url
    )
}

private fun String.toStatus(): StatusBO = when (this.lowercase()) {
    "alive" -> StatusBO.ALIVE
    "dead" -> StatusBO.DEAD
    else -> StatusBO.UNKNOWN
}

private fun String.toGender(): GenderBO = when (this.lowercase()) {
    "female" -> GenderBO.FEMALE
    "male" -> GenderBO.MALE
    "genderless" -> GenderBO.GENDERLESS
    else -> GenderBO.UNKNOWN
}

fun LocationDto.mapToBO(): OriginBO {
    return OriginBO(
        name = name,
        url = url,
    )
}
