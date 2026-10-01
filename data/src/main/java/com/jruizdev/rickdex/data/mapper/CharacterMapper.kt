package com.jruizdev.rickdex.data.mapper

import com.jruizdev.rickdex.data.model.CharacterDto
import com.jruizdev.rickdex.data.model.CharacterResponseDto
import com.jruizdev.rickdex.data.model.InfoDto
import com.jruizdev.rickdex.domain.model.CharacterBO
import com.jruizdev.rickdex.domain.model.CharacterResponseBO
import com.jruizdev.rickdex.domain.model.InfoBO
import java.net.URI

fun CharacterResponseDto.mapToBO(): CharacterResponseBO {
    return CharacterResponseBO(
        info = info.mapToBO(),
        characters = results.map { it.mapToBO() }
    )
}

fun InfoDto.mapToBO(): InfoBO {
    val page = next?.let { url ->
        try {
            URI(url).query?.split("&")
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
        /*TODO*/
    )
}
