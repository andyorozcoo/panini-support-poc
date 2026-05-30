package com.panini.support.core

object AppConstants {
    object Api {
        /** Android emulator reaches the host machine through 10.0.2.2. */
        const val BASE_URL = "http://10.0.2.2:5147/"

        object Paths {
            const val AUTH_LOGIN = "api/auth/login"
            const val TICKETS = "api/tickets"
            const val TICKET_DETAIL = "api/tickets/{ticketId}"
            const val TICKET_STATUS = "api/tickets/{ticketId}/status"
            const val TICKET_PRIORITY = "api/tickets/{ticketId}/priority"
        }
    }
}