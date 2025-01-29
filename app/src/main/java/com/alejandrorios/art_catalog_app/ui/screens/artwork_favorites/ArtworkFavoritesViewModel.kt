package com.alejandrorios.art_catalog_app.ui.screens.artwork_favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandrorios.art_catalog_app.data.db.ArtworksDao
import com.alejandrorios.art_catalog_app.data.utils.AppDispatchers
import com.alejandrorios.art_catalog_app.domain.models.Artwork
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ArtworkFavoritesViewModel @Inject constructor(
    private val dao: ArtworksDao,
    private val dispatcher: AppDispatchers,
    @Named("enableDelay") private val enableDelay: Boolean
) : ViewModel() {
    private val _uiState = MutableStateFlow(ArtworkFavoritesUIState())
    val uiState: StateFlow<ArtworkFavoritesUIState> = _uiState.asStateFlow()

    // In case any other unexpected error
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _uiState.update { currentState ->
            currentState.copy(isLoading = false, errorMessage = "An error occurred")
        }
    }

    fun loadArtworks() {
        _uiState.update { currentState ->
            currentState.copy(isLoading = true)
        }

        viewModelScope.launch(dispatcher.io + coroutineExceptionHandler) {
            // This delay is only for view purposes in the project, nothing else, added `enableDelay` to disable this on tests
            if (enableDelay) {
                delay(500)
            }

            dao.getArtworks().collect { artworks ->
                _uiState.update { currentState ->
                    currentState.copy(isLoading = false, artworks = artworks)
                }
            }
        }
    }

    fun removeArtwork(artwork: Artwork) {
        viewModelScope.launch(dispatcher.io + coroutineExceptionHandler) {
            dao.deleteArtwork(artwork)
        }
    }
}
