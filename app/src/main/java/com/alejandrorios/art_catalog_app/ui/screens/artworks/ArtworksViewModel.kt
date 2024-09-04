package com.alejandrorios.art_catalog_app.ui.screens.artworks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandrorios.art_catalog_app.data.utils.CallResponse.Failure
import com.alejandrorios.art_catalog_app.data.utils.CallResponse.Success
import com.alejandrorios.art_catalog_app.domain.repository.ArtRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ArtworksViewModel @Inject constructor(
    private val artRepository: ArtRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ArtworksUiState())
    val uiState: StateFlow<ArtworksUiState> = _uiState.asStateFlow()

    fun fetchArtworks() {
        _uiState.update { currentState ->
            currentState.copy(isLoading = true)
        }

        viewModelScope.launch {
            when (val result = artRepository.getArtworks(1)) {
                is Failure -> _uiState.update { currentState ->
                    currentState.copy(isLoading = false, errorMessage = result.t.message)
                }

                is Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            artworksCurrentPage = result.data.pagination.currentPage,
                            artworksTotalPages = result.data.pagination.totalPages,
                            artworks = result.data.data,
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }

    fun loadMoreResults() {
        val artworks = _uiState.value.artworks.toMutableList()
        val currentPage = _uiState.value.artworksCurrentPage

        if (_uiState.value.artworksTotalPages == currentPage) return

        _uiState.update { currentState ->
            currentState.copy(isLoadingMore = true)
        }

        viewModelScope.launch {
            when (val result = artRepository.getArtworks(currentPage + 1)) {
                is Failure -> _uiState.update { currentState ->
                    currentState.copy(isLoadingMore = false, errorMessage = result.t.message)
                }

                is Success -> {
                    artworks.addAll(result.data.data)
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoadingMore = false,
                            artworksCurrentPage = result.data.pagination.currentPage,
                            artworks = artworks,
                        )
                    }
                }
            }
        }
    }
}
