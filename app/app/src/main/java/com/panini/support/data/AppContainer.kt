package com.panini.support.data

import com.panini.support.data.repository.TicketRepository

/**
 * Small manual container for this PoC. It keeps dependencies easy to find
 * without adding a DI framework that is not needed for the current scope.
 */
object AppContainer {
    val ticketRepository: TicketRepository by lazy { TicketRepository() }
}