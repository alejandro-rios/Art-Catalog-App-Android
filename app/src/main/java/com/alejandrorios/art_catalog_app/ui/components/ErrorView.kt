package com.alejandrorios.art_catalog_app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alejandrorios.art_catalog_app.R
import com.alejandrorios.art_catalog_app.ui.theme.Art_catalog_appTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.error_title),
    message: String = stringResource(R.string.error_message),
    onRetry: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.warning), contentDescription = "")
        VerticalSpacer()
        Text(title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        VerticalSpacer()
        Text(message, textAlign = TextAlign.Center, fontSize = 18.sp)
        VerticalSpacer(spacing = 20.dp)
        onRetry?.let {
            Button(
                modifier = Modifier.fillMaxWidth().semantics { testTagsAsResourceId = true }.testTag("retryButton"),
                shape = RoundedCornerShape(6.dp),
                onClick = it
            ) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    }
}

@Preview(name = "ErrorView without retry button", showBackground = true)
@Composable
fun ErrorViewWithNoRetryPreview() {
    Art_catalog_appTheme {
        ErrorView(modifier = Modifier.padding(horizontal = 16.dp))
    }
}

@Preview(name = "ErrorView with retry button", showBackground = true)
@Composable
fun ErrorViewWithRetryPreview() {
    Art_catalog_appTheme {
        ErrorView(modifier = Modifier.padding(horizontal = 16.dp), onRetry = {})
    }
}
