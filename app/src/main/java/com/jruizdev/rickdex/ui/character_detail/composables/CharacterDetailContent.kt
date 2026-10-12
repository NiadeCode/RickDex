package com.jruizdev.rickdex.ui.character_detail.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jruizdev.rickdex.R
import com.jruizdev.rickdex.ui.character_detail.model.CharacterDetailVO
import com.jruizdev.rickdex.ui.character_detail.model.GenderVO
import com.jruizdev.rickdex.ui.character_detail.model.StatusVO
import com.jruizdev.rickdex.ui.characters.composables.CharacterListText
import com.jruizdev.rickdex.ui.composables.AliveIcon
import com.jruizdev.rickdex.ui.composables.GenderIcon
import com.jruizdev.rickdex.ui.composables.ImageWithBorder
import com.jruizdev.rickdex.ui.theme.RickDexTheme

@Composable
fun CharacterDetailContent(
    modifier: Modifier = Modifier,
    character: CharacterDetailVO,
) {
    Column(
        modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        Row {
            ImageWithBorder(
                modifier = Modifier
                    .size(125.dp)
                    .align(Alignment.CenterVertically), character.image
            )
            Column {
                CharacterListText(
                    title = R.string.name,
                    value = character.name,
                    endIcon = { GenderIcon(value = character.gender) })
                CharacterListText(
                    title = R.string.status,
                    value = character.status.value,
                    endIcon = { AliveIcon(value = character.status) })
                CharacterListText(title = R.string.species, value = character.species)
            }
        }
        CharacterListText(title = R.string.origin, value = character.origin)
        CharacterListText(title = R.string.location, value = character.location)
        CharacterListText(title = R.string.gender, value = character.gender.value, endIcon = {
            GenderIcon(value = character.gender)
        })
        if (character.episodes.isNotEmpty()) {
            CharacterListText(title = R.string.episodes, value = character.episodes)
        }
    }
}

@Preview
@Composable
private fun PreviewCharacterDetailContent() {
    RickDexTheme(darkTheme = true) {
        CharacterDetailContent(
            character = CharacterDetailVO(
                id = 1,
                name = "Rick Sanchez",
                species = "Human",
                origin = "Earth (C-137)",
                location = "Citadel of Ricks",
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                status = StatusVO.DEAD,
                gender = GenderVO.MALE,
                episodes = "1, 2",
            )
        )
    }
}
