package com.panini.support.ui.screens.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.panini.support.core.FeatureFlags
import com.panini.support.data.model.Ticket
import com.panini.support.data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TicketListUiState(
    val tickets: List<Ticket> = emptyList(),
    val canCreateTickets: Boolean = true,
    val canUpdatePriority: Boolean = true
)

class TicketListViewModel(
    private val ticketRepository: TicketRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TicketListUiState())
    val uiState: StateFlow<TicketListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            ticketRepository.tickets.collect { tickets ->
                _uiState.value = _uiState.value.copy(tickets = tickets)
            }
        }
        viewModelScope.launch {
            FeatureFlags.state.collect { flags ->
                _uiState.value = _uiState.value.copy(
                    canCreateTickets = flags.enableTicketCreation,
                    canUpdatePriority = flags.enablePriorityUpdates
                )
            }
        }
    }

    fun setTicketCreationEnabled(enabled: Boolean) {
        FeatureFlags.setTicketCreationEnabled(enabled)
    }

    fun setPriorityUpdatesEnabled(enabled: Boolean) {
        FeatureFlags.setPriorityUpdatesEnabled(enabled)
    }
}

class TicketListViewModelFactory(
    private val ticketRepository: TicketRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        TicketListViewModel(ticketRepository) as T
}