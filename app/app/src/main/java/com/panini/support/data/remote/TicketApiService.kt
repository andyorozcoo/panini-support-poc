package com.panini.support.data.remote

import com.panini.support.core.AppConstants
import com.panini.support.data.remote.model.CreateTicketRequestDto
import com.panini.support.data.remote.model.LoginRequestDto
import com.panini.support.data.remote.model.TicketDto
import com.panini.support.data.remote.model.UpdateTicketPriorityRequestDto
import com.panini.support.data.remote.model.UpdateTicketStatusRequestDto
import com.panini.support.data.remote.model.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TicketApiService {
    @POST(AppConstants.Api.Paths.AUTH_LOGIN)
    suspend fun login(@Body request: LoginRequestDto): Response<UserDto>

    @GET(AppConstants.Api.Paths.TICKETS)
    suspend fun getTickets(
        @Query("status") status: String? = null,
        @Query("priority") priority: String? = null,
        @Query("category") category: String? = null
    ): Response<List<TicketDto>>

    @POST(AppConstants.Api.Paths.TICKETS)
    suspend fun createTicket(@Body request: CreateTicketRequestDto): Response<TicketDto>

    @GET(AppConstants.Api.Paths.TICKET_DETAIL)
    suspend fun getTicketById(@Path("ticketId") ticketId: String): Response<TicketDto>

    @PATCH(AppConstants.Api.Paths.TICKET_STATUS)
    suspend fun updateTicketStatus(
        @Path("ticketId") ticketId: String,
        @Body request: UpdateTicketStatusRequestDto
    ): Response<TicketDto>

    @PATCH(AppConstants.Api.Paths.TICKET_PRIORITY)
    suspend fun updateTicketPriority(
        @Path("ticketId") ticketId: String,
        @Body request: UpdateTicketPriorityRequestDto
    ): Response<TicketDto>
}