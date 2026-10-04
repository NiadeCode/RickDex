package com.jruizdev.rickdex.ui.characters.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jruizdev.rickdex.ui.characters.CharacterFieldVO
import com.jruizdev.rickdex.ui.characters.CharacterVO
import com.jruizdev.rickdex.ui.composables.ImageWithBorder
import com.jruizdev.rickdex.ui.theme.RickDexTheme

@Composable
fun CharacterListRow(character: CharacterVO, onCharacterClick: (Int) -> Unit) {
    Row(
        Modifier
            .padding(16.dp)
            .clickable { onCharacterClick(character.id) }) {
        ImageWithBorder(
            modifier = Modifier
                .size(125.dp)
                .align(Alignment.CenterVertically),
            character.image
        )
        Column {
            character.fields.forEach { field ->
                CharacterListText(characterFieldVO = field)
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
private fun CharacterListRowPreview() {
    RickDexTheme() {
        CharacterListRow(
            character = CharacterVO(
                1, fields = listOf(
                    CharacterFieldVO(
                        title = com.jruizdev.rickdex.R.string.name, value = "Rick Sanchez"
                    ), CharacterFieldVO(
                        title = com.jruizdev.rickdex.R.string.status, value = "Alive"
                    ), CharacterFieldVO(
                        title = com.jruizdev.rickdex.R.string.species, value = "Human"
                    )
                ), "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
            ), onCharacterClick = { /* preview-only */ })
    }

}