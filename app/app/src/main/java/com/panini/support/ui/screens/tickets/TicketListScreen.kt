package com.panini.support.ui.screens.tickets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.panini.support.data.AppContainer
import com.panini.support.data.model.Ticket
import java.time.format.DateTimeFormatter

private val TicketDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

@Composable
fun TicketListScreen(
    onCreateTicketClick: () -> Unit,
    onTicketClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TicketListViewModel = viewModel(
        factory = TicketListViewModelFactory(AppContainer.ticketRepository)
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Tickets de soporte",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            if (uiState.canCreateTickets) {
                Button(onClick = onCreateTicketClick) { Text("Crear") }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(uiState.tickets, key = { it.id }) { ticket ->
                TicketCard(ticket = ticket, onClick = { onTicketClick(ticket.id) })
            }
        }
    }
}

@Composable
private fun TicketCard(ticket: Ticket, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = ticket.title, fontWeight = FontWeight.Bold)
            Text(text = ticket.supplierName, style = MaterialTheme.typography.bodyMedium)
            Text(text = "${ticket.priority.label} | ${ticket.status.label} | ${ticket.category.label}")
            Text(
                text = "Creado: ${ticket.createdAt.format(TicketDateFormatter)}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}