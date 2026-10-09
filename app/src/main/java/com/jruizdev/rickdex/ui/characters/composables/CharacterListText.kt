package com.jruizdev.rickdex.ui.characters.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jruizdev.rickdex.ui.characters.CharacterFieldVO
import com.jruizdev.rickdex.ui.theme.RickDexTheme

@Composable
fun CharacterListText(
    modifier: Modifier = Modifier, characterFieldVO: CharacterFieldVO
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp, end = 16.dp, top = 8.dp),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground,
            text = stringResource(characterFieldVO.title)
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 24.dp),
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = characterFieldVO.value
            )
        }
    }
}

@Preview()
@Composable
private fun CharacterListTextPreview() {
    Column() {

        RickDexTheme(darkTheme = true) {
            Box(Modifier.background(MaterialTheme.colorScheme.background))
            CharacterListText(
                characterFieldVO = CharacterFieldVO(
                    title = com.jruizdev.rickdex.R.string.name, value = "Rick Sanchez"
                )
            )
        }

        RickDexTheme(darkTheme = false) {
            Box(Modifier.background(MaterialTheme.colorScheme.background)) {
                CharacterListText(
                    characterFieldVO = CharacterFieldVO(
                        title = com.jruizdev.rickdex.R.string.name, value = "Rick Sanchez"
                    )
                )
            }
        }
    }
}