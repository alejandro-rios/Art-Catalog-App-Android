package com.alejandrorios.art_catalog_app.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import com.alejandrorios.art_catalog_app.R
import com.alejandrorios.art_catalog_app.domain.models.Artwork
import com.alejandrorios.art_catalog_app.ui.theme.LightPrimary
import kotlinx.coroutines.launch

/**
 * This composable works as a simple list, the main feature is the pagination option
 * @param onClickItem method to execute when the item is clicked.
 * @param onNewItems method to execute when the list has reached the end, like pagination.
 * @param semanticTag tag used for Maestro tests
 */
@Composable
fun PaginationListView(
    items: List<Artwork>,
    isLoadingItems: Boolean = false,
    onClickItem: (Int) -> Unit,
    onNewItems: (() -> Unit)? = null,
    semanticTag: String = "paginationListView",
) {
    CustomListView(
        items = items,
        isLoadingItems = isLoadingItems,
        onClickItem = onClickItem,
        onNewItems = onNewItems,
        semanticTag = semanticTag
    )
}

/**
 * This composable works as a simple list, the main feature is the swipe to delete
 * @param onClickItem method to execute when the item is clicked.
 * @param onDeleteItem method to execute when you want to delete an item, if the method is not null tiles will have the
 * "swipe to delete" animation.
 * @param semanticTag tag used for Maestro tests
 */
@Composable
fun SwipeableListView(
    items: List<Artwork>,
    onClickItem: (Int) -> Unit,
    onDeleteItem: ((Artwork) -> Unit)? = null,
    semanticTag: String = "swipeableListView",
) {
    CustomListView(
        items = items,
        onClickItem = onClickItem,
        onDeleteItem = onDeleteItem,
        semanticTag = semanticTag
    )
}

/**
 * This composable works as a simple list, the main features are the pagination and the swipe to delete
 * @param onClickItem method to execute when the item is clicked.
 * @param onDeleteItem method to execute when you want to delete an item, if the method is not null tiles will have the
 * "swipe to delete" animation.
 * @param onNewItems method to execute when the list has reached the end, like pagination.
 * @param semanticTag tag used for Maestro tests
 *
 * marked as private to restrict access
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun CustomListView(
    items: List<Artwork>,
    isLoadingItems: Boolean = false,
    onClickItem: (Int) -> Unit,
    onDeleteItem: ((Artwork) -> Unit)? = null,
    onNewItems: (() -> Unit)? = null,
    semanticTag: String = "artworkList",
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val reachedBottom: Boolean by remember { derivedStateOf { listState.reachedBottom() } }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom && onNewItems != null) onNewItems.invoke()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .semantics { testTagsAsResourceId = true }
                .testTag(semanticTag)
        ) {
            itemsIndexed(items) { _, item: Artwork ->
                if (onDeleteItem != null) {
                    SwipeToDeleteContainer(item = item, onDelete = { onDeleteItem.invoke(it) }) {
                        ArtworkTile(artwork = item) {
                            onClickItem(item.id)
                        }
                    }
                } else {
                    ArtworkTile(artwork = item) {
                        onClickItem(item.id)
                    }
                }
            }

            if (isLoadingItems) item {
                CircularProgressIndicator(modifier = Modifier.padding(vertical = 16.dp))
            }
        }

        AnimatedVisibility(
            visible = !listState.isScrollingUp(),
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
        ) {
            FloatingActionButton(
                modifier = Modifier.size(50.dp),
                containerColor = LightPrimary,
                contentColor = Color.White,
                onClick = {
                    scope.launch {
                        listState.scrollToItem(0)
                    }
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_north),
                    contentDescription = "back to top"
                )
            }
        }
    }
}

/***
 * Resource:
 * [Endless Scrolling in Android with Jetpack Compose](https://medium.com/@giorgos.patronas1/endless-scrolling-in-android-with-jetpack-compose-af1f55a03d1a)
 */
internal fun LazyListState.reachedBottom(buffer: Int = 1): Boolean {
    val lastVisibleItem = this.layoutInfo.visibleItemsInfo.lastOrNull()
    return lastVisibleItem?.index != 0 && lastVisibleItem?.index == this.layoutInfo.totalItemsCount - buffer
}

/**
 * Resource:
 * [How to create a jump to top feature with jetpack compose](https://medium.com/@gsaillen95/how-to-create-a-jump-to-top-feature-with-jetpack-compose-2ed487b30087)
 */
@Composable
fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }

    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}
