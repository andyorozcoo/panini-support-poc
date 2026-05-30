package com.panini.support.ui.screens.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.panini.support.core.FeatureFlags
import com.panini.support.data.model.Ticket
import com.panini.support.data.model.TicketPriority
import com.panini.support.data.model.TicketStatus
import com.panini.support.data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TicketDetailUiState(
    val ticket: Ticket? = null,
    val canUpdatePriority: Boolean = FeatureFlags.enablePriorityUpdates,
    val message: String? = null
)

class TicketDetailViewModel(
    private val ticketId: String,
    private val ticketRepository: TicketRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TicketDetailUiState())
    val uiState: StateFlow<TicketDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            ticketRepository.tickets.collect { tickets ->
                _uiState.value = _uiState.value.copy(
                    ticket = tickets.firstOrNull { ticket -> ticket.id == ticketId }
                )
            }
        }
    }

    fun updateStatus(status: TicketStatus) {
        val success = ticketRepository.updateStatus(ticketId, status)
        _uiState.value = _uiState.value.copy(message = if (success) "Estado actualizado" else "Ticket no encontrado")
    }

    fun updatePriority(priority: TicketPriority) {
        if (!FeatureFlags.enablePriorityUpdates) return
        val success = ticketRepository.updatePriority(ticketId, priority)
        _uiState.value = _uiState.value.copy(message = if (success) "Prioridad actualizada" else "Ticket no encontrado")
    }
}

class TicketDetailViewModelFactory(
    private val ticketId: String,
    private val ticketRepository: TicketRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        TicketDetailViewModel(ticketId, ticketRepository) as T
}