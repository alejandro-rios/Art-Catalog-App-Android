package com.alejandrorios.art_catalog_app.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alejandrorios.art_catalog_app.R

/**
 * Pinch to zoom feature based on a combination of this:
 * [code](https://github.com/philipplackner/ComposePinchZoomRotate/blob/master/app/src/main/java/com/plcoding/composepinchzoomrotate/MainActivity.kt),
 * [StackOverflow](https://stackoverflow.com/a/72210341/3729124)
 */
@Composable
fun ZoomableImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    isZoomed: (Boolean) -> Unit,
) {
    // Controls the zoom levels
    var scale by remember { mutableFloatStateOf(1f) }
    // Manages the pan position
    var offset by remember { mutableStateOf(Offset.Zero) }
    // Tracks if the image is currently being zoomed
    var isZooming by remember { mutableStateOf(false) }

    // Animation setup
    val animatedScale = animateFloatAsState(
        label = "animate scale",
        targetValue = if (isZooming) scale else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )
    val animatedOffsetX = animateFloatAsState(
        label = "animate offset X",
        targetValue = if (isZooming) offset.x else 0f,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )
    val animatedOffsetY = animateFloatAsState(
        label = "animate offset Y",
        targetValue = if (isZooming) offset.y else 0f,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )

    BoxWithConstraints(
        modifier = modifier.fillMaxWidth()
    ) {
        // Handles pinch-to-zoom and pan gestures and updates scale and offset based on user input
        val state = rememberTransformableState { zoomChange, panChange, _ ->
            scale = (scale * zoomChange).coerceIn(1f, 5f)

            val extraWidth = (scale - 1) * constraints.maxWidth
            val extraHeight = (scale - 1) * constraints.maxHeight

            val maxX = extraWidth / 2
            val maxY = extraHeight / 2

            offset = Offset(
                x = (offset.x + scale * panChange.x).coerceIn(-maxX, maxX),
                y = (offset.y + scale * panChange.y).coerceIn(-maxY, maxY),
            )
            isZooming = true

            //This to tell the composable to move the image in the Z-index
            isZoomed(scale > 1.0f)
        }

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.artwork_thumb_description),
            placeholder = painterResource(R.drawable.placeholder),
            error = painterResource(R.drawable.no_image),
            alignment = Alignment.Center,
            contentScale = ContentScale.FillHeight,
            modifier = modifier
                .graphicsLayer {
                    scaleX = animatedScale.value
                    scaleY = animatedScale.value
                    translationX = animatedOffsetX.value
                    translationY = animatedOffsetY.value
                }
                .transformable(state)
                .pointerInput(Unit) {
                    // Continuously monitors for pointer (finger) events
                    awaitEachGesture {
                        awaitFirstDown()
                        while (true) {
                            val event = awaitPointerEvent()
                            // Detects when all fingers are lifted off the screen
                            if (event.changes.all { it.changedToUp() }) {
                                // All pointers are up, reset the zoom
                                scale = 1f
                                offset = Offset.Zero
                                isZooming = false
                                isZoomed(false)
                            }
                        }
                    }
                }
        )
    }
}
