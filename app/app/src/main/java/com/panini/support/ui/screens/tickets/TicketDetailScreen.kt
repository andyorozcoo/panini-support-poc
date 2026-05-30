package com.panini.support.ui.screens.tickets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.panini.support.data.AppContainer
import com.panini.support.data.model.TicketPriority
import com.panini.support.data.model.TicketStatus
import java.time.format.DateTimeFormatter

private val DetailDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

@Composable
fun TicketDetailScreen(
    ticketId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TicketDetailViewModel = viewModel(
        key = ticketId,
        factory = TicketDetailViewModelFactory(ticketId, AppContainer.ticketRepository)
    )
) {
    val uiState by viewModel.uiState.collectAsState()
    val ticket = uiState.ticket

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        OutlinedButton(onClick = onBackClick) { Text("Volver") }

        if (ticket == null) {
            Text("Ticket no encontrado")
            return@Column
        }

        Text(ticket.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("Proveedor: ${ticket.supplierName}")
        Text("Categoria: ${ticket.category.label}")
        Text("Prioridad: ${ticket.priority.label}")
        Text("Estado: ${ticket.status.label}")
        Text("Creado: ${ticket.createdAt.format(DetailDateFormatter)}")
        Text(ticket.description)

        Text("Actualizar estado", fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { viewModel.updateStatus(TicketStatus.InProgress) }) { Text("Proceso") }
            Button(onClick = { viewModel.updateStatus(TicketStatus.WaitingSupplier) }) { Text("Proveedor") }
            Button(onClick = { viewModel.updateStatus(TicketStatus.Resolved) }) { Text("Resolver") }
        }

        if (uiState.canUpdatePriority) {
            Text("Actualizar prioridad", fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { viewModel.updatePriority(TicketPriority.Critical) }) { Text("Critica") }
                Button(onClick = { viewModel.updatePriority(TicketPriority.High) }) { Text("Alta") }
                Button(onClick = { viewModel.updatePriority(TicketPriority.Medium) }) { Text("Media") }
            }
        }

        uiState.message?.let { Text(it, color = MaterialTheme.colorScheme.primary) }
    }
}