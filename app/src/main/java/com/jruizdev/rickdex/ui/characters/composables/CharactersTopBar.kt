package com.jruizdev.rickdex.ui.characters.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jruizdev.rickdex.R
import com.jruizdev.rickdex.ui.theme.RickDexTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersTopBar() {
    TopAppBar(
        title = {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(R.string.characters_title)
            )
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ), actions = {
            IconButton(onClick = {/*TODO*/ }) {
                Icon(
                    painterResource(R.drawable.baseline_search_24),
                    contentDescription = stringResource(R.string.search)
                )
            }
        })
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFF0000)
@Composable
private fun CharactersTopBarDarkThemePreview() {
    RickDexTheme(darkTheme = true) {
        CharactersTopBar()
    }
}


@Preview(showSystemUi = true)
@Composable
private fun CharactersTopBarLightThemePreview() {
    RickDexTheme(darkTheme = false) {
        CharactersTopBar()
    }
}