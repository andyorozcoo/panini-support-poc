package com.panini.support.data.model

import java.time.LocalDateTime

/**
 * Domain model used by the UI. It represents an internal support request
 * related to Panini supplier, inventory, distribution, or logistics incidents.
 */
data class Ticket(
    val id: String,
    val title: String,
    val description: String,
    val supplierName: String,
    val category: TicketCategory,
    val priority: TicketPriority,
    val status: TicketStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime? = null
)

enum class TicketCategory(val label: String) {
    Inventory("Inventario"),
    Distribution("Distribucion"),
    Supplier("Proveedor"),
    Logistics("Logistica"),
    Quality("Calidad")
}

enum class TicketPriority(val label: String, val rank: Int) {
    Critical("Critica", 4),
    High("Alta", 3),
    Medium("Media", 2),
    Low("Baja", 1)
}

enum class TicketStatus(val label: String) {
    Open("Abierto"),
    InProgress("En proceso"),
    WaitingSupplier("Esperando proveedor"),
    Resolved("Resuelto"),
    Closed("Cerrado")
}