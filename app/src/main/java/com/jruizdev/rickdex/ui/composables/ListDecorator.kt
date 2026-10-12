package com.jruizdev.rickdex.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListDecorator() {
    HorizontalDivider(
        modifier = Modifier.padding(start = 64.dp),
        thickness = 2.dp,
        color = MaterialTheme.colorScheme.outline
    )
    Spacer(modifier = Modifier.size(2.dp))
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 32.dp),
        thickness = 2.dp,
        color = MaterialTheme.colorScheme.secondaryContainer
    )
}
