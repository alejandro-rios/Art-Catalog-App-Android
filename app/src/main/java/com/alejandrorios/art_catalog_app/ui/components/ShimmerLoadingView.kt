package com.alejandrorios.art_catalog_app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alejandrorios.art_catalog_app.ui.theme.Art_catalog_appTheme

@Composable
fun ShimmerLoadingView(modifier: Modifier = Modifier, numberOfItems : Int = 4) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(numberOfItems) {
            ShimmerListItem(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShimmerLoadingViewPreview() {
    Art_catalog_appTheme {
        ShimmerLoadingView()
    }
}
