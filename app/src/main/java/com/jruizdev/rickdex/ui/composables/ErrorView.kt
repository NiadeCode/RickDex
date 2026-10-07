package com.jruizdev.rickdex.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.jruizdev.rickdex.R

@Composable
fun ErrorView(
    modifier: Modifier = Modifier, message: String = "¡Me convertí en un error, Morty!"
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp, 0.dp, 12.dp, 12.dp),
            color = MaterialTheme.colorScheme.errorContainer,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onErrorContainer),
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.7F),
                text = message,
                color = MaterialTheme.colorScheme.error
            )
        }
        Spacer(Modifier.size(8.dp))
        AsyncImage(
            modifier = Modifier.size(50.dp),
            model = R.drawable.pickle_rick_transparent_edgetrimmed,
            contentDescription = "imagen de error"
        )
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun PreviewError() {
    ErrorView()
}
