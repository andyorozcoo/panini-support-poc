package com.panini.support.ui.screens.tickets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
            .padding(16.dp)
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        OutlinedButton(onClick = onBackClick) { Text("Volver") }

        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Crear ticket",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Registra incidentes de proveedores, inventario o distribucion.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = uiState.title,
                    onValueChange = viewModel::onTitleChange,
                    label = { Text("Titulo") },
                    placeholder = { Text("Ej. Faltante de sobres en bodega regional") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = uiState.supplierName,
                    onValueChange = viewModel::onSupplierChange,
                    label = { Text("Proveedor") },
                    placeholder = { Text("Ej. Distribuidora Norte") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = uiState.description,
                    onValueChange = viewModel::onDescriptionChange,
                    label = { Text("Descripcion") },
                    placeholder = { Text("Describe el impacto operativo del incidente") },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        ChoiceSection(title = "Categoria: ${uiState.category.label}") {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { viewModel.onCategoryChange(TicketCategory.Inventory) }, modifier = Modifier.weight(1f)) { Text("Inventario") }
                Button(onClick = { viewModel.onCategoryChange(TicketCategory.Distribution) }, modifier = Modifier.weight(1f)) { Text("Distribucion") }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { viewModel.onCategoryChange(TicketCategory.Supplier) }, modifier = Modifier.weight(1f)) { Text("Proveedor") }
                Button(onClick = { viewModel.onCategoryChange(TicketCategory.Logistics) }, modifier = Modifier.weight(1f)) { Text("Logistica") }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { viewModel.onCategoryChange(TicketCategory.Quality) }, modifier = Modifier.weight(1f)) { Text("Calidad") }
            }
        }

        ChoiceSection(title = "Prioridad: ${uiState.priority.label}") {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { viewModel.onPriorityChange(TicketPriority.Critical) }, modifier = Modifier.weight(1f)) { Text("Critica") }
                Button(onClick = { viewModel.onPriorityChange(TicketPriority.High) }, modifier = Modifier.weight(1f)) { Text("Alta") }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { viewModel.onPriorityChange(TicketPriority.Medium) }, modifier = Modifier.weight(1f)) { Text("Media") }
                Button(onClick = { viewModel.onPriorityChange(TicketPriority.Low) }, modifier = Modifier.weight(1f)) { Text("Baja") }
            }
        }

        uiState.errorMessage?.let { Text(it, color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold) }

        Button(onClick = viewModel::createTicket, modifier = Modifier.fillMaxWidth()) {
            Text("Guardar ticket")
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun ChoiceSection(title: String, content: @Composable ColumnScope.() -> Unit) {
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