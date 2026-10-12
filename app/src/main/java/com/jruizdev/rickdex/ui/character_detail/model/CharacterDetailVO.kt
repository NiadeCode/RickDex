package com.jruizdev.rickdex.ui.character_detail.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.jruizdev.rickdex.R

data class CharacterDetailVO(
    val id: Int,
    val image: String,
    val name: String,
    val status: StatusVO,
    val species: String,
    val origin: String,
    val location: String,
    val gender: GenderVO,
    val episodes: String
)

enum class GenderVO(val value: String, @DrawableRes val icon: Int) {
    FEMALE("Female", R.drawable.ic_female),
    MALE("Male", R.drawable.ic_male),
    GENDERLESS("Genderless", R.drawable.ic_genderless),
    UNKNOWN("Unknown", R.drawable.round_question_mark_24)
}

enum class StatusVO(val value: String, val tint: Color) {
    ALIVE("Alive", tint = Color.Green),
    DEAD("Dead", tint = Color.Red),
    UNKNOWN("Unknown", tint = Color.Gray)
}
