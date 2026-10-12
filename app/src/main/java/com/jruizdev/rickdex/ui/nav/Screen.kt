package com.jruizdev.rickdex.ui.nav

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object Home : Screen

    @Serializable
    data class CharacterDetail(val itemId: String) : Screen}