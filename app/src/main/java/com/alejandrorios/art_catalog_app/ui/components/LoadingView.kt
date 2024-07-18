package com.alejandrorios.art_catalog_app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alejandrorios.art_catalog_app.ui.theme.Art_catalog_appTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize().semantics { testTagsAsResourceId = true }.testTag("loadingView")
    ) {
        CircularProgressIndicator(modifier = Modifier.width(64.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingViewPreview() {
    Art_catalog_appTheme {
        LoadingView()
    }
}
