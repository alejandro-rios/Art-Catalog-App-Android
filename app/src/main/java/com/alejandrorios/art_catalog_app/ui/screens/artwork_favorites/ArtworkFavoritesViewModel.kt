package com.alejandrorios.art_catalog_app.ui.screens.artwork_favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandrorios.art_catalog_app.data.db.ArtworksDao
import com.alejandrorios.art_catalog_app.domain.models.Artwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArtworkFavoritesViewModel(private val dao: ArtworksDao) : ViewModel() {

    private val _uiState = MutableStateFlow(ArtworkFavoritesUIState())
    val uiState: StateFlow<ArtworkFavoritesUIState> = _uiState.asStateFlow()

    fun loadArtworks() {
        _uiState.update { currentState ->
            currentState.copy(isLoading = true)
        }

        viewModelScope.launch(Dispatchers.IO) {
            // This delay is only for view purposes in the project, nothing else
            delay(500)
            dao.getArtworks().collect { artworks ->
                _uiState.update { currentState ->
                    currentState.copy(isLoading = false, artworks = artworks)
                }
            }
        }
    }

    fun removeArtwork(artwork: Artwork) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteArtwork(artwork)
        }
    }
}
