package com.panini.support.ui.screens.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.panini.support.data.model.TicketCategory
import com.panini.support.data.model.TicketPriority
import com.panini.support.data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CreateTicketUiState(
    val title: String = "",
    val description: String = "",
    val supplierName: String = "",
    val category: TicketCategory = TicketCategory.Inventory,
    val priority: TicketPriority = TicketPriority.Medium,
    val errorMessage: String? = null,
    val createdTicketId: String? = null
)

class CreateTicketViewModel(
    private val ticketRepository: TicketRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateTicketUiState())
    val uiState: StateFlow<CreateTicketUiState> = _uiState.asStateFlow()

    fun onTitleChange(value: String) { _uiState.value = _uiState.value.copy(title = value, errorMessage = null) }
    fun onDescriptionChange(value: String) { _uiState.value = _uiState.value.copy(description = value, errorMessage = null) }
    fun onSupplierChange(value: String) { _uiState.value = _uiState.value.copy(supplierName = value, errorMessage = null) }
    fun onCategoryChange(value: TicketCategory) { _uiState.value = _uiState.value.copy(category = value) }
    fun onPriorityChange(value: TicketPriority) { _uiState.value = _uiState.value.copy(priority = value) }

    fun createTicket() {
        val current = _uiState.value
        if (current.title.isBlank() || current.description.isBlank() || current.supplierName.isBlank()) {
            _uiState.value = current.copy(errorMessage = "Complete titulo, descripcion y proveedor.")
            return
        }

        val created = ticketRepository.createTicket(
            title = current.title,
            description = current.description,
            supplierName = current.supplierName,
            category = current.category,
            priority = current.priority
        )
        _uiState.value = current.copy(createdTicketId = created.id, errorMessage = null)
    }
}

class CreateTicketViewModelFactory(
    private val ticketRepository: TicketRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CreateTicketViewModel(ticketRepository) as T
}