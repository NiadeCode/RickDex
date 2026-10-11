package com.jruizdev.rickdex.ui.characters.composables

import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jruizdev.rickdex.R
import com.jruizdev.rickdex.ui.composables.SearchBar
import com.jruizdev.rickdex.ui.theme.RickDexTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersTopBar(onQueryChange: (String, Boolean) -> Unit) {

    var searchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    BackHandler(enabled = searchActive) {
        searchActive = false
        searchQuery = ""
        onQueryChange("", true)
    }

    TopAppBar(
        title = {
            if (searchActive) {
                SearchBar(
                    modifier = Modifier,
                    searchQuery = searchQuery,
                    onSearchQueryChange = { newQuery ->
                        searchQuery = newQuery
                        onQueryChange(newQuery, false)
                    },
                    onSearchSubmit = {
                        onQueryChange(searchQuery, true)
                    })
            } else {
                Text(
                    style = MaterialTheme.typography.titleLarge.copy(
                        shadow = Shadow(
                            color = MaterialTheme.colorScheme.secondary,
                            offset = Offset(5f, 3f),
                            blurRadius = 3f
                        ),
                    ), text = stringResource(R.string.characters_title)
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
        ), actions = {
            if (searchActive) {
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f),
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    onClick = {
                        searchActive = false
                        searchQuery = ""
                        onQueryChange("", true)
                    },

                    ) {
                    Icon(
                        painterResource(R.drawable.baseline_close_24),
                        contentDescription = stringResource(R.string.close_search)
                    )
                }
            } else {
                IconButton(
                    onClick = { searchActive = true }, colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f),
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(
                        painterResource(R.drawable.baseline_search_24),
                        contentDescription = stringResource(R.string.search)
                    )
                }
            }
        })
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFF0000)
@Composable
private fun CharactersTopBarDarkThemePreview() {
    RickDexTheme(darkTheme = true) {
        CharactersTopBar({ _, _ -> /*preview only*/ })
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CharactersTopBarLightThemePreview() {
    RickDexTheme(darkTheme = false) {
        CharactersTopBar({ _, _ -> /*preview only*/ })
    }
}
