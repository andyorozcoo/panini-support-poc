package com.panini.support.ui.screens.tickets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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

        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Proveedor", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text(ticket.supplierName)
                Text("Categoria: ${ticket.category.label}")
                Text("Prioridad: ${ticket.priority.label}")
                Text("Estado: ${ticket.status.label}")
                Text("Creado: ${ticket.createdAt.format(DetailDateFormatter)}")
                ticket.updatedAt?.let { Text("Actualizado: ${it.format(DetailDateFormatter)}") }
            }
        }

        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Descripcion", fontWeight = FontWeight.Bold)
                Text(ticket.description)
            }
        }

        ActionSection(title = "Actualizar estado") {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { viewModel.updateStatus(TicketStatus.InProgress) }, modifier = Modifier.weight(1f)) { Text("Proceso") }
                Button(onClick = { viewModel.updateStatus(TicketStatus.WaitingSupplier) }, modifier = Modifier.weight(1f)) { Text("Proveedor") }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { viewModel.updateStatus(TicketStatus.Resolved) }, modifier = Modifier.weight(1f)) { Text("Resuelto") }
                Button(onClick = { viewModel.updateStatus(TicketStatus.Closed) }, modifier = Modifier.weight(1f)) { Text("Cerrado") }
            }
        }

        if (uiState.canUpdatePriority) {
            ActionSection(title = "Actualizar prioridad") {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = { viewModel.updatePriority(TicketPriority.Critical) }, modifier = Modifier.weight(1f)) { Text("Critica") }
                    Button(onClick = { viewModel.updatePriority(TicketPriority.High) }, modifier = Modifier.weight(1f)) { Text("Alta") }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = { viewModel.updatePriority(TicketPriority.Medium) }, modifier = Modifier.weight(1f)) { Text("Media") }
                    Button(onClick = { viewModel.updatePriority(TicketPriority.Low) }, modifier = Modifier.weight(1f)) { Text("Baja") }
                }
            }
        }

        uiState.message?.let { Text(it, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold) }
    }
}

@Composable
private fun ActionSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            content()
        }
    }
}