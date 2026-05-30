# Decisiones arquitectonicas

## Decision 1: Arquitectura MVVM

Se utiliza MVVM porque es una arquitectura comun en Android moderno y mantiene una separacion clara entre UI, estado y datos.

Responsabilidades:

- `Screen`: muestra informacion y captura acciones del usuario.
- `ViewModel`: expone `UiState`, valida acciones simples y llama al Repository.
- `Repository`: administra la fuente de datos mock y queda preparado para Retrofit.
- `DTOs`: representan el contrato futuro con el backend.

Esta estructura es suficiente para el alcance de la PoC y no introduce complejidad innecesaria.

## Decision 2: Mock Repository como fuente inicial de datos

Como no hay backend real, el Repository puede mantener una lista interna de tickets usando `MutableStateFlow`.

Esto permite que la aplicacion sea funcional y al mismo tiempo mantenga una frontera clara para reemplazar mocks por API real en el futuro.

## Decision 3: StateFlow para estado reactivo

Se usa `StateFlow` porque encaja con Compose y Coroutines. El listado puede observar cambios sin recargas manuales.

Casos cubiertos:

- creacion de ticket;
- cambio de estado;
- cambio de prioridad;
- reordenamiento automatico del listado.

## Decision 4: Feature Flags locales

Se implementan como una configuracion simple en codigo. Esto evita depender de servicios externos y permite demostrar el concepto claramente.

Ejemplos:

- `enableTicketCreation`;
- `enablePriorityUpdates`.

## Decision 5: Retrofit preparado, no conectado todavia

El alcance solicitado incluye networking layer, DTOs y contratos, pero no backend funcional. Por eso Retrofit puede quedar listo como estructura, mientras la app usa mocks para operar.

La decision reduce riesgo de implementacion y mantiene el proyecto facil de evolucionar.

## Decision 6: Documentacion corta y especifica

La documentacion se mantiene enfocada en lo que otro ingeniero necesita saber: alcance, flujo, decisiones y evolucion futura. No se agrega texto generico ni explicaciones desconectadas del sistema.

## Evolucion futura

La solucion puede evolucionar de forma incremental:

1. Reemplazar Mock Repository por Retrofit.
2. Agregar autenticacion real.
3. Persistir tickets localmente si se requiere offline.
4. Mover Feature Flags a configuracion remota.
5. Agregar roles administrativos si el negocio lo necesita.

