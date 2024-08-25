package com.alejandrorios.art_catalog_app.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alejandrorios.art_catalog_app.R
import com.alejandrorios.art_catalog_app.ui.theme.LightPrimary

@Composable
fun BackToTop(goToTop: () -> Unit) {
    FloatingActionButton(
        modifier = Modifier.size(50.dp),
        onClick = goToTop,
        containerColor = LightPrimary,
        contentColor = Color.White
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_north),
            contentDescription = "back to top"
        )
    }
}
