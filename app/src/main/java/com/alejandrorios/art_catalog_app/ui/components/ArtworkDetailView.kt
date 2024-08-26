package com.alejandrorios.art_catalog_app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.alejandrorios.art_catalog_app.R
import com.alejandrorios.art_catalog_app.domain.models.ArtworkDetail
import com.alejandrorios.art_catalog_app.ui.theme.Art_catalog_appTheme
import com.alejandrorios.art_catalog_app.ui.theme.BlueLink


@Composable
fun ArtworkDetailView(
    modifier: Modifier = Modifier,
    artworkDetails: ArtworkDetail,
) {
    var isZoomed by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ZoomableImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .zIndex(if (isZoomed) 1f else 0f),
            imageUrl = artworkDetails.imageUrl
        ) { zoomed ->
            isZoomed = zoomed
        }
        VerticalSpacer(spacing = 16.dp)
        Text(text = artworkDetails.title, maxLines = 2, fontSize = 24.sp)
        VerticalSpacer()
        artworkDetails.artistDisplay?.let {
            Text(text = it)
            VerticalSpacer(spacing = 20.dp)
        }
        artworkDetails.dimensions?.let { dimensions ->
            Text(text = stringResource(R.string.dimensions), fontSize = 18.sp)
            VerticalSpacer()
            Text(text = dimensions)
            VerticalSpacer(spacing = 20.dp)
        }
        Text(text = stringResource(R.string.description), fontSize = 18.sp)
        VerticalSpacer()
        if (!artworkDetails.description.isNullOrBlank()) {
            ExpandableText(
                text = artworkDetails.description,
                fontSize = 16.sp,
                collapsedMaxLine = 5,
                showMoreStyle = SpanStyle(color = BlueLink),
                showLessStyle = SpanStyle(color = BlueLink),
                textAlign = TextAlign.Justify
            )
        } else {
            Text(text = stringResource(R.string.no_description), fontSize = 16.sp, fontStyle = FontStyle.Italic)
        }
        VerticalSpacer(spacing = 20.dp)
    }
}

@Preview(showBackground = true)
@Composable
fun ArtworkDetailViewPreview() {
    Art_catalog_appTheme {
        ArtworkDetailView(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            artworkDetails = ArtworkDetail(
                id = 12472,
                title = "Stone",
                imageUrl = "",
                artistDisplay = "Joan Miró\nSpanish, 1893–1983",
                placeOfOrigin = "Spain",
                description = stringResource(id = R.string.lorem_ipsum),
                shortDescription = "Alma Thomas was enthralled by astronauts and outer space. This painting, made when she was 81, showcases that fascination through her signature style of short, rhythmic strokes of paint. “Color is life, and light is the mother of color,” she once proclaimed. In 1972, she became the first African American woman to have a solo exhibition at the Whitney Museum of American Art in New York.",
                dimensions = "82.7 × 41 × 12.7 cm (32 1/2 × 16 1/4 × 5 in.)",
                artworkTypeTitle = "Sculpture",
                artistTitle = "Joan Miró",
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArtworkDetailViewWithNoDescriptionPreview() {
    Art_catalog_appTheme {
        ArtworkDetailView(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            artworkDetails = ArtworkDetail(
                id = 12472,
                title = "Stone",
                imageUrl = "",
                artistDisplay = "Joan Miró\nSpanish, 1893–1983",
                placeOfOrigin = "Spain",
                description = null,
                shortDescription = null,
                dimensions = "82.7 × 41 × 12.7 cm (32 1/2 × 16 1/4 × 5 in.)",
                artworkTypeTitle = "Sculpture",
                artistTitle = "Joan Miró",
            ),
        )
    }
}
