# Flujo general del sistema

## Vista general

La aplicacion funciona como una PoC movil sin backend real. La UI consume datos desde un Repository mock, pero la estructura queda preparada para que el Repository use Retrofit en una fase posterior.

```text
Usuario
  -> Pantalla Compose
  -> ViewModel
  -> Repository
  -> Mock Data / futura API Retrofit
  -> StateFlow
  -> ViewModel UiState
  -> Pantalla Compose actualizada
```

## Login simulado

1. El usuario ingresa credenciales.
2. El ViewModel valida que los campos no esten vacios.
3. El Repository simula la autenticacion.
4. Si es correcto, la app navega al listado de tickets.
5. No se implementan tokens reales porque no hay backend en el alcance.

## Listado de tickets

1. La pantalla observa el estado del ViewModel.
2. El ViewModel observa el flujo de tickets del Repository.
3. Los tickets se muestran con titulo, proveedor, categoria, prioridad, estado y fecha.
4. El orden prioriza tickets mas urgentes.

## Detalle de ticket

1. El usuario selecciona un ticket.
2. La app navega al detalle usando el identificador del ticket.
3. El ViewModel obtiene el ticket desde el Repository.
4. La pantalla muestra informacion ampliada y acciones disponibles.

## Creacion de ticket

1. El usuario abre la pantalla de creacion.
2. Completa titulo, descripcion, proveedor, categoria y prioridad.
3. El ViewModel valida campos basicos.
4. El Repository crea el ticket mock.
5. El flujo de tickets se actualiza.
6. El listado refleja el nuevo ticket automaticamente.

## Actualizacion de estado

1. El usuario cambia el estado del ticket.
2. El ViewModel envia la accion al Repository.
3. El Repository actualiza el ticket.
4. La pantalla de detalle y el listado reciben el cambio por el flujo reactivo.

## Actualizacion de prioridad

1. El usuario cambia la prioridad si el Feature Flag lo permite.
2. El Repository actualiza el ticket.
3. La lista se reordena por prioridad.
4. Compose recompone la UI automaticamente.

## Integracion futura con backend

Cuando exista backend, el cambio esperado es reemplazar internamente el origen de datos del Repository:

```text
MockTicketDataSource -> RetrofitTicketApi
```

Las pantallas y ViewModels no deberian cambiar de forma significativa porque seguiran hablando con el Repository.
