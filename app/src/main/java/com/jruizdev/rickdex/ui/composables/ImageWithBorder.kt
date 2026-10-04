package com.jruizdev.rickdex.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.jruizdev.rickdex.ui.theme.portalColorBrush

@Composable
fun ImageWithBorder(
    modifier: Modifier = Modifier, characterImage: String
) {
    Box(
        modifier = modifier.border(
            shape = CircleShape, border = BorderStroke(
                width = 2.dp, brush = portalColorBrush
            )
        ), contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            modifier = modifier.clip(CircleShape),
            model = ImageRequest.Builder(LocalContext.current).data(characterImage).build(),
            contentDescription = null,
            placeholder = ColorPainter(MaterialTheme.colorScheme.primary)
        )
    }
}