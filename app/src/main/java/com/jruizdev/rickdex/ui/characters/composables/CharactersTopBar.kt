package com.jruizdev.rickdex.ui.characters.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.jruizdev.rickdex.R
import com.jruizdev.rickdex.ui.theme.RickDexTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersTopBar(onQueryChange: (String, Boolean) -> Unit) {

    var searchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

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
                    }
                )
            } else {
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    text = stringResource(R.string.characters_title)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        actions = {
            if (searchActive) {
                IconButton(onClick = {
                    searchActive = false
                    searchQuery = ""
                    onQueryChange("", true)
                }) {
                    Icon(
                        painterResource(R.drawable.baseline_close_24),
                        contentDescription = stringResource(R.string.close_search)
                    )
                }
            } else {
                IconButton(onClick = { searchActive = true }) {
                    Icon(
                        painterResource(R.drawable.baseline_search_24),
                        contentDescription = stringResource(R.string.search)
                    )
                }
            }
        }
    )
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchSubmit: () -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        placeholder = {
            Text(
                text = stringResource(R.string.search_placeholder),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
            )
        },
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onPrimary
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
            cursorColor = MaterialTheme.colorScheme.onPrimary,
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                onSearchSubmit()
            }
        ),
        trailingIcon = {
            IconButton(onClick = {
                keyboardController?.hide()
                onSearchSubmit()
            }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_search_24),
                    contentDescription = stringResource(R.string.search),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        modifier = modifier.fillMaxWidth()
    )
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
