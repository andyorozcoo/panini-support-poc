package com.panini.support.data.mock

import com.panini.support.data.model.Ticket
import com.panini.support.data.model.TicketCategory
import com.panini.support.data.model.TicketPriority
import com.panini.support.data.model.TicketStatus
import java.time.LocalDateTime

object MockTickets {
    val initialTickets = listOf(
        Ticket(
            id = "ticket-001",
            title = "Faltante de sobres en entrega regional",
            description = "El punto de venta de San Carlos reporta 18 cajas incompletas de sobres del album FIFA 2026. Se requiere validar el despacho contra la guia del proveedor.",
            supplierName = "Distribuidora Norte",
            category = TicketCategory.Inventory,
            priority = TicketPriority.Critical,
            status = TicketStatus.Open,
            createdAt = LocalDateTime.of(2026, 5, 30, 8, 15)
        ),
        Ticket(
            id = "ticket-002",
            title = "Retraso en ruta hacia tiendas del Caribe",
            description = "La ruta programada para Limon lleva mas de 24 horas de atraso. Los comercios reportan agotamiento de paquetes promocionales.",
            supplierName = "Logistica Caribe Express",
            category = TicketCategory.Distribution,
            priority = TicketPriority.High,
            status = TicketStatus.InProgress,
            createdAt = LocalDateTime.of(2026, 5, 30, 7, 40),
            updatedAt = LocalDateTime.of(2026, 5, 30, 8, 5)
        ),
        Ticket(
            id = "ticket-003",
            title = "Proveedor envia cajas sin codigo de lote",
            description = "Se recibieron cajas de albumes sin codigo de lote visible. El equipo de soporte necesita confirmacion antes de liberar inventario a puntos de venta.",
            supplierName = "Impresos Centroamericanos",
            category = TicketCategory.Supplier,
            priority = TicketPriority.High,
            status = TicketStatus.WaitingSupplier,
            createdAt = LocalDateTime.of(2026, 5, 29, 16, 20)
        ),
        Ticket(
            id = "ticket-004",
            title = "Diferencia entre inventario fisico y sistema",
            description = "La bodega central registra 240 sobres menos que el conteo esperado luego del cierre de recepcion del viernes.",
            supplierName = "Bodega Central Panini",
            category = TicketCategory.Inventory,
            priority = TicketPriority.Medium,
            status = TicketStatus.Open,
            createdAt = LocalDateTime.of(2026, 5, 29, 13, 10)
        ),
        Ticket(
            id = "ticket-005",
            title = "Albumes con paginas desprendidas",
            description = "Tres puntos de venta reportan albumes con fallas de encuadernacion. Se requiere revisar lote y coordinar reposicion.",
            supplierName = "Editorial Mundial 2026",
            category = TicketCategory.Quality,
            priority = TicketPriority.Medium,
            status = TicketStatus.Resolved,
            createdAt = LocalDateTime.of(2026, 5, 28, 10, 30),
            updatedAt = LocalDateTime.of(2026, 5, 29, 9, 0)
        ),
        Ticket(
            id = "ticket-006",
            title = "Solicitud de reprogramacion de entrega",
            description = "El proveedor solicita mover la entrega de paquetes premium para evitar cruce con inventario de cajas regulares.",
            supplierName = "Transporte Pacifico",
            category = TicketCategory.Logistics,
            priority = TicketPriority.Low,
            status = TicketStatus.Closed,
            createdAt = LocalDateTime.of(2026, 5, 27, 11, 45),
            updatedAt = LocalDateTime.of(2026, 5, 28, 15, 25)
        )
    )
}