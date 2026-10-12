package com.jruizdev.rickdex.ui.character_detail.composables


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.jruizdev.rickdex.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailTopBar(
    modifier: Modifier = Modifier,
    onBack: () -> Boolean,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(R.string.character_detail_title)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        navigationIcon = {
            IconButton(onClick = { onBack() }) {
                Icon(
                    painterResource(R.drawable.outline_arrow_back_24),
                    contentDescription = stringResource(R.string.back)
                )
            }
        },
    )
}
