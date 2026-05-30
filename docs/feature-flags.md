# Feature Flags

## Objetivo

Los Feature Flags permiten activar o desactivar funcionalidades durante pruebas internas sin modificar varias partes del sistema.

En esta PoC se usan para demostrar control de alcance y capacidad de evolucion del producto.

## Flags implementados

- `enableTicketCreation`: controla si se puede crear tickets.
- `enablePriorityUpdates`: controla si se puede cambiar la prioridad de un ticket.

## Como se controlan

Los flags se mantienen localmente en la aplicacion mediante `FeatureFlags`, pero no requieren editar codigo durante la demo. La aplicacion incluye una pantalla dedicada de Feature Flags, accesible desde el boton Configurar del listado de tickets. Desde esa vista se pueden activar o desactivar:

- creacion de tickets;
- cambio de prioridad.

Esto permite simular un escenario de pruebas internas donde Panini habilita o deshabilita funciones sin modificar multiples pantallas.

## Por que no usar Remote Config

Para una PoC de corto alcance no se justifica implementar Firebase Remote Config, backend de configuracion o almacenamiento persistente de flags.

La solucion actual es suficiente para:

- demostrar el patron;
- controlar alcance del MVP desde la app;
- explicar facilmente el flujo en el video;
- permitir evolucion futura.

## Evolucion futura

En una version empresarial posterior, estos flags podrian moverse a:

- Firebase Remote Config;
- endpoint de configuracion del backend;
- configuracion por rol de usuario.

La interfaz de uso podria mantenerse similar para no afectar las pantallas.