package com.panini.support.ui.screens.tickets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        OutlinedButton(onClick = onBackClick) { Text("Volver") }

        if (ticket == null) {
            Text("Ticket no encontrado")
            return@Column
        }

        Text(ticket.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)

        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Proveedor: ${ticket.supplierName}")
                Text("Categoria: ${ticket.category.label}")
                Text("Prioridad: ${ticket.priority.label}")
                Text("Estado: ${ticket.status.label}")
                Text("Creado: ${ticket.createdAt.format(DetailDateFormatter)}")
                ticket.updatedAt?.let { Text("Actualizado: ${it.format(DetailDateFormatter)}") }
            }
        }

        Text("Descripcion", fontWeight = FontWeight.Bold)
        Text(ticket.description)

        Text("Actualizar estado", fontWeight = FontWeight.Bold)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { viewModel.updateStatus(TicketStatus.InProgress) }) { Text("En proceso") }
                Button(onClick = { viewModel.updateStatus(TicketStatus.WaitingSupplier) }) { Text("Proveedor") }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { viewModel.updateStatus(TicketStatus.Resolved) }) { Text("Resuelto") }
                Button(onClick = { viewModel.updateStatus(TicketStatus.Closed) }) { Text("Cerrado") }
            }
        }

        if (uiState.canUpdatePriority) {
            Text("Actualizar prioridad", fontWeight = FontWeight.Bold)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = { viewModel.updatePriority(TicketPriority.Critical) }) { Text("Critica") }
                    Button(onClick = { viewModel.updatePriority(TicketPriority.High) }) { Text("Alta") }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = { viewModel.updatePriority(TicketPriority.Medium) }) { Text("Media") }
                    Button(onClick = { viewModel.updatePriority(TicketPriority.Low) }) { Text("Baja") }
                }
            }
            Text(
                text = "Al cambiar la prioridad, el listado se reordena automaticamente.",
                style = MaterialTheme.typography.bodySmall
            )
        }

        uiState.message?.let { Text(it, color = MaterialTheme.colorScheme.primary) }
    }
}