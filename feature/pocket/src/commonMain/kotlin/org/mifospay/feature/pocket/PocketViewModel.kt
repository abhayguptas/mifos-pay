package org.mifospay.feature.pocket

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import org.mifospay.core.common.DataState
import org.mifospay.core.data.repository.PocketRepository
import org.mifospay.core.ui.utils.BaseViewModel
import org.mifospay.core.model.domain.pocket.Pocket

class PocketViewModel(
    private val repository: PocketRepository
) : BaseViewModel<PocketState, PocketEvent, PocketAction>(
    initialState = PocketState()
) {

    val pocketState = repository.getPockets()
        .mapLatest { dataState ->
            when (dataState) {
                is DataState.Loading -> PocketState.ViewState.Loading
                is DataState.Error -> PocketState.ViewState.Error(dataState.exception.message.toString())
                is DataState.Success -> PocketState.ViewState.Content(dataState.data)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PocketState.ViewState.Loading
        )

    override fun handleAction(action: PocketAction) {
        when (action) {
            is PocketAction.LinkAccountClicked -> {
                sendEvent(PocketEvent.ShowLinkAccountsBottomSheet)
            }
            is PocketAction.ManagePocketClicked -> {
                sendEvent(PocketEvent.NavigateToManagePocket)
            }
        }
    }
}

data class PocketState(
    val dialogState: DialogState? = null
) {
    sealed interface DialogState {
        data object Loading : DialogState
        data class Error(val message: String) : DialogState
    }

    sealed interface ViewState {
        data object Loading : ViewState
        data class Error(val message: String) : ViewState
        data class Content(val pockets: List<Pocket>) : ViewState
    }
}

sealed interface PocketEvent {
    data object ShowLinkAccountsBottomSheet : PocketEvent
    data object NavigateToManagePocket : PocketEvent
}

sealed interface PocketAction {
    data object LinkAccountClicked : PocketAction
    data object ManagePocketClicked : PocketAction
}
