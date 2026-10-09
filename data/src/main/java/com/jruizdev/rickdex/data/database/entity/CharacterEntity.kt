package com.jruizdev.rickdex.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    @Embedded(prefix = "origin_")
    val origin: LocationEntity,
    @Embedded(prefix = "location_")
    val location: LocationEntity,
    val image: String,
    val episodes: String, // Comma-separated list of episode URLs
    val url: String,
    val created: String
)

data class LocationEntity(
    val name: String,
    val url: String
)
