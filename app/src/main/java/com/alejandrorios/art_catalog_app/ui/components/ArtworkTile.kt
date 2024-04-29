package com.alejandrorios.art_catalog_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alejandrorios.art_catalog_app.R
import com.alejandrorios.art_catalog_app.domain.models.Artwork
import com.alejandrorios.art_catalog_app.ui.theme.Art_catalog_appTheme
import com.alejandrorios.art_catalog_app.ui.theme.GrayBackground
import com.alejandrorios.art_catalog_app.ui.theme.GrayText

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ArtworkTile(artwork: Artwork, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .semantics { testTagsAsResourceId = true }.testTag("artworkTile")
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .height(130.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .background(color = GrayBackground)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(artwork.imageUrl)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder),
                    error = painterResource(R.drawable.no_image),
                    contentDescription = stringResource(R.string.artwork_tile_thumb_description),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.fillMaxSize()
                )
            }
            HorizontalSpacer()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = artwork.title,
                    maxLines = 3,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W400,
                    color = Color.Black
                )
                Text(
                    text = artwork.artworkTypeTitle,
                    fontSize = 12.sp,
                    color = GrayText,
                )
                VerticalSpacer(spacing = 8.dp)
                artwork.artistTitle?.let { artist ->
                    Text(
                        text = artist,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        maxLines = 1,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Preview(name = "Artwork tile with artist", showBackground = true)
@Composable
fun ArtworkTileWithArtistPreview() {
    val artwork = Artwork(
        id = 1234,
        title = "Some art",
        artistTitle = "Pepito puentes",
        artworkTypeTitle = "painting",
        imageUrl = "",
    )
    Art_catalog_appTheme {
        ArtworkTile(artwork = artwork, onClick = {})
    }
}

@Preview(name = "Artwork tile without artist", showBackground = true)
@Composable
fun ArtworkTileWithoutArtistPreview() {
    val artwork = Artwork(
        id = 1234,
        title = "Some art",
        artistTitle = null,
        artworkTypeTitle = "painting",
        imageUrl = "",
    )
    Art_catalog_appTheme {
        ArtworkTile(artwork = artwork, onClick = {})
    }
}

