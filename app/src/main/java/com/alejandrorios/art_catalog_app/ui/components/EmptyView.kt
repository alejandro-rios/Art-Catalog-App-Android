package com.alejandrorios.art_catalog_app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alejandrorios.art_catalog_app.R
import com.alejandrorios.art_catalog_app.ui.theme.Art_catalog_appTheme

@Composable
fun EmptyView(
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.no_favorites),
    message: String = stringResource(R.string.no_favorites_message),
    image: Painter = painterResource(id = R.drawable.empty_box),
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = image, contentDescription = "")
        VerticalSpacer()
        Text(title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        VerticalSpacer()
        Text(
            message,
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyViewPreview() {
    Art_catalog_appTheme {
        EmptyView(modifier = Modifier.padding(horizontal = 16.dp))
    }
}
