package org.mifospay.feature.pocket

import org.mifospay.core.model.domain.pocket.Pocket

sealed interface PocketUiState {
    object Loading : PocketUiState
    data class Success(val pockets: List<Pocket>) : PocketUiState
    data class Error(val message: String) : PocketUiState
}
