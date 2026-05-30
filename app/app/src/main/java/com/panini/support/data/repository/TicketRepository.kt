package com.panini.support.data.repository

import com.panini.support.data.mock.MockTickets
import com.panini.support.data.model.Ticket
import com.panini.support.data.model.TicketCategory
import com.panini.support.data.model.TicketPriority
import com.panini.support.data.model.TicketStatus
import java.time.LocalDateTime
import java.util.UUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TicketRepository {
    private val _tickets = MutableStateFlow(sortTickets(MockTickets.initialTickets))
    val tickets: StateFlow<List<Ticket>> = _tickets.asStateFlow()

    fun getTicketById(ticketId: String): Ticket? =
        _tickets.value.firstOrNull { ticket -> ticket.id == ticketId }

    fun createTicket(
        title: String,
        description: String,
        supplierName: String,
        category: TicketCategory,
        priority: TicketPriority
    ): Ticket {
        val ticket = Ticket(
            id = UUID.randomUUID().toString(),
            title = title.trim(),
            description = description.trim(),
            supplierName = supplierName.trim(),
            category = category,
            priority = priority,
            status = TicketStatus.Open,
            createdAt = LocalDateTime.now()
        )

        _tickets.update { current -> sortTickets(current + ticket) }
        return ticket
    }

    fun updateStatus(ticketId: String, status: TicketStatus): Boolean {
        var updated = false
        _tickets.update { current ->
            sortTickets(
                current.map { ticket ->
                    if (ticket.id == ticketId) {
                        updated = true
                        ticket.copy(status = status, updatedAt = LocalDateTime.now())
                    } else {
                        ticket
                    }
                }
            )
        }
        return updated
    }

    fun updatePriority(ticketId: String, priority: TicketPriority): Boolean {
        var updated = false
        _tickets.update { current ->
            sortTickets(
                current.map { ticket ->
                    if (ticket.id == ticketId) {
                        updated = true
                        ticket.copy(priority = priority, updatedAt = LocalDateTime.now())
                    } else {
                        ticket
                    }
                }
            )
        }
        return updated
    }

    private fun sortTickets(items: List<Ticket>): List<Ticket> =
        items.sortedWith(
            compareByDescending<Ticket> { ticket -> ticket.priority.rank }
                .thenByDescending { ticket -> ticket.createdAt }
        )
}