# Feature Flags

## Objetivo

Los Feature Flags permiten activar o desactivar funcionalidades durante pruebas internas sin modificar varias partes del sistema.

En esta PoC se usan para demostrar control de alcance y capacidad de evolucion del producto.

## Flags propuestos

```kotlin
object FeatureFlags {
    const val enableTicketCreation = true
    const val enablePriorityUpdates = true
}
```

## Flag: enableTicketCreation

Controla si el usuario puede crear tickets.

Cuando esta activo:

- se muestra la accion para crear ticket;
- el formulario permite guardar;
- el Repository agrega el ticket al flujo reactivo.

Cuando esta inactivo:

- se oculta o deshabilita la accion;
- la pantalla puede mostrar un mensaje simple indicando que la funcion no esta disponible.

## Flag: enablePriorityUpdates

Controla si el usuario puede cambiar la prioridad de un ticket.

Cuando esta activo:

- se permite modificar prioridad;
- el listado se reordena automaticamente.

Cuando esta inactivo:

- la prioridad se muestra solo como lectura;
- se evita llamar a la accion de actualizacion.

## Por que flags locales

Para una PoC de corto alcance no se justifica implementar Remote Config, backend de configuracion o almacenamiento persistente de flags.

Los flags locales son suficientes para:

- demostrar el patron;
- controlar alcance del MVP;
- explicar facilmente el flujo en el video;
- permitir evolucion futura.

## Evolucion futura

En una version empresarial posterior, estos flags podrian moverse a:

- Firebase Remote Config;
- endpoint de configuracion del backend;
- configuracion por rol de usuario.

La interfaz de uso podria mantenerse similar para no afectar las pantallas.

