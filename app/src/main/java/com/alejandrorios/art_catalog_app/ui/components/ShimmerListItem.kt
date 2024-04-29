package com.alejandrorios.art_catalog_app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandrorios.art_catalog_app.ui.utils.shimmerEffect

/**
 * Original code was copied and modified from here:
 * https://github.com/philipplackner/ShimmerEffectCompose
 */
@Composable
fun ShimmerListItem(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp)
            .shimmerEffect()
    )
}
