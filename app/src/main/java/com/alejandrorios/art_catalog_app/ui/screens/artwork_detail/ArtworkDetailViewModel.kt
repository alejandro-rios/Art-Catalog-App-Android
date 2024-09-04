package com.alejandrorios.art_catalog_app.ui.screens.artwork_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandrorios.art_catalog_app.data.db.ArtworksDao
import com.alejandrorios.art_catalog_app.data.utils.CallResponse.Failure
import com.alejandrorios.art_catalog_app.data.utils.CallResponse.Success
import com.alejandrorios.art_catalog_app.domain.repository.ArtRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ArtworkDetailViewModel @Inject constructor(
    private val artRepository: ArtRepository,
    private val dao: ArtworksDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArtworkDetailUIState())
    val uiState: StateFlow<ArtworkDetailUIState> = _uiState.asStateFlow()

    fun getArtworkDetail(artworkId: Int) {
        _uiState.update { currentState ->
            currentState.copy(isLoading = true)
        }
        viewModelScope.launch {
            // This delay is only for view purposes in the project, nothing else
            delay(500)
            when (val result = artRepository.getArtworkDetails(artworkId)) {
                is Failure -> _uiState.update { currentState ->
                    currentState.copy(isLoading = false, errorMessage = result.t.message)
                }

                is Success -> {
                    _uiState.update { currentState ->
                        print(result.data.description)
                        currentState.copy(
                            artworkDetails = result.data,
                            errorMessage = null
                        )
                    }

                    isSavedLocally(artworkId)
                }
            }
        }
    }

    private fun isSavedLocally(artworkId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.findArtworkById(artworkId).collect {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        isSaved = it != null
                    )
                }
            }
        }
    }

    fun saveArtwork() {
        val artwork = _uiState.value.artworkDetails!!.mapAsArtwork()
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertArtwork(artwork)

            _uiState.update { currentState ->
                currentState.copy(isSaved = true)
            }
        }
    }

    fun removeArtwork() {
        val artwork = _uiState.value.artworkDetails!!.mapAsArtwork()
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteArtwork(artwork)

            _uiState.update { currentState ->
                currentState.copy(isSaved = false)
            }
        }
    }
}
