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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.panini.support.data.AppContainer
import com.panini.support.data.model.TicketCategory
import com.panini.support.data.model.TicketPriority

@Composable
fun CreateTicketScreen(
    onBackClick: () -> Unit,
    onTicketCreated: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateTicketViewModel = viewModel(
        factory = CreateTicketViewModelFactory(AppContainer.ticketRepository)
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.createdTicketId) {
        uiState.createdTicketId?.let(onTicketCreated)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedButton(onClick = onBackClick) { Text("Volver") }
        Text("Crear ticket", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = uiState.title,
            onValueChange = viewModel::onTitleChange,
            label = { Text("Titulo") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = uiState.supplierName,
            onValueChange = viewModel::onSupplierChange,
            label = { Text("Proveedor") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = uiState.description,
            onValueChange = viewModel::onDescriptionChange,
            label = { Text("Descripcion") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Text("Categoria: ${uiState.category.label}", fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { viewModel.onCategoryChange(TicketCategory.Inventory) }) { Text("Inventario") }
            Button(onClick = { viewModel.onCategoryChange(TicketCategory.Distribution) }) { Text("Distribucion") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { viewModel.onCategoryChange(TicketCategory.Supplier) }) { Text("Proveedor") }
            Button(onClick = { viewModel.onCategoryChange(TicketCategory.Logistics) }) { Text("Logistica") }
        }

        Text("Prioridad: ${uiState.priority.label}", fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { viewModel.onPriorityChange(TicketPriority.Critical) }) { Text("Critica") }
            Button(onClick = { viewModel.onPriorityChange(TicketPriority.High) }) { Text("Alta") }
            Button(onClick = { viewModel.onPriorityChange(TicketPriority.Medium) }) { Text("Media") }
        }

        uiState.errorMessage?.let { Text(it, color = MaterialTheme.colorScheme.error) }

        Button(onClick = viewModel::createTicket, modifier = Modifier.fillMaxWidth()) {
            Text("Guardar ticket")
        }
    }
}