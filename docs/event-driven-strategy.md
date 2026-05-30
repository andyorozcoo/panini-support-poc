# Estrategia basada en eventos

## Objetivo

La aplicacion debe reaccionar automaticamente cuando cambia la informacion de tickets, sin obligar al usuario a recargar pantallas manualmente.

## Estrategia elegida

Para esta PoC se usa una estrategia reactiva simple basada en `StateFlow`.

El Repository mantiene el estado principal de tickets en un `MutableStateFlow<List<Ticket>>`. Los ViewModels observan ese flujo y exponen un `UiState` a las pantallas Compose.

## Flujo de creacion de ticket

1. El usuario completa el formulario en `CreateTicketScreen`.
2. La pantalla llama al metodo del ViewModel.
3. El ViewModel valida los campos minimos.
4. El ViewModel llama al Repository.
5. El Repository agrega el ticket al `MutableStateFlow`.
6. El listado recibe automaticamente la nueva lista.
7. Compose recompone la pantalla.

Resultado: el ticket nuevo aparece sin reiniciar la pantalla ni hacer una recarga manual.

## Flujo de actualizacion de prioridad

1. El usuario cambia la prioridad desde el detalle o una accion permitida.
2. El ViewModel llama al Repository.
3. El Repository actualiza el ticket dentro del flujo.
4. La lista se ordena nuevamente por prioridad y fecha.
5. La UI observa el cambio y se actualiza automaticamente.

Resultado: los tickets mas urgentes suben en el listado de forma inmediata.

## Orden sugerido de prioridad

1. `Critical`
2. `High`
3. `Medium`
4. `Low`

Si dos tickets tienen la misma prioridad, se puede ordenar por fecha de creacion descendente o ascendente segun la necesidad de la pantalla.


## Evolucion futura

La misma idea puede evolucionar hacia:

- Room con `Flow` para persistencia local;
- llamadas Retrofit que refresquen el estado del Repository;
- WebSockets o push notifications si el negocio requiere actualizaciones multiusuario en tiempo real.
