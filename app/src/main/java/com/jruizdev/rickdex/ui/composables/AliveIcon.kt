package com.jruizdev.rickdex.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jruizdev.rickdex.ui.character_detail.model.GenderVO
import com.jruizdev.rickdex.ui.character_detail.model.StatusVO

@Composable
fun AliveIcon(modifier: Modifier = Modifier, value: StatusVO) {
    Spacer(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .size(16.dp)
            .shadow(1.dp, CircleShape)
            .clip(CircleShape)
            .background(value.tint)
    )
}

@Composable
fun GenderIcon(modifier: Modifier = Modifier, value: GenderVO) {
    Icon(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .size(16.dp),
        painter = painterResource(value.icon),
        contentDescription = null
    )
}