package com.panini.support.data.remote.model

/** DTOs that mirror contracts/tickets-api.yaml for future backend integration. */
data class TicketDto(
    val id: String,
    val title: String,
    val description: String,
    val supplierName: String,
    val category: String,
    val priority: String,
    val status: String,
    val createdAt: String,
    val updatedAt: String? = null
)

data class CreateTicketRequestDto(
    val title: String,
    val description: String,
    val supplierName: String,
    val category: String,
    val priority: String
)

data class UpdateTicketStatusRequestDto(
    val status: String
)

data class UpdateTicketPriorityRequestDto(
    val priority: String
)

data class ErrorResponseDto(
    val message: String
)