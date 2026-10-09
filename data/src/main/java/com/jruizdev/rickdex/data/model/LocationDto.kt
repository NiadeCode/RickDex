package com.jruizdev.rickdex.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val name: String,
    val url: String
)
