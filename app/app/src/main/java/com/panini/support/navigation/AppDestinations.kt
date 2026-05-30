package com.panini.support.navigation

object AppDestinations {
    const val LOGIN = "login"
    const val TICKET_LIST = "tickets"
    const val CREATE_TICKET = "tickets/create"
    const val TICKET_DETAIL = "tickets/{ticketId}"

    fun ticketDetailRoute(ticketId: String): String = "tickets/$ticketId"
}