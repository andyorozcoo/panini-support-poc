# Panini Support Tickets PoC

Aplicacion movil de prueba de concepto para la gestion interna de tickets de soporte relacionados con proveedores, distribucion e inventario del album oficial de la Copa Mundial FIFA 2026.

El proyecto esta orientado a una entrega con criterio empresarial: simple, mantenible, facil de explicar y preparada para que otro desarrollador pueda continuarla sin reorganizar toda la aplicacion.

## Alcance del MVP

La aplicacion contempla:

- autenticacion simulada;
- listado de tickets de soporte;
- detalle de ticket;
- creacion de tickets;
- actualizacion de estado;
- manejo basico de prioridades;
- datos simulados realistas;
- comunicacion reactiva basada en eventos con StateFlow;
- Feature Flags para activar o desactivar funcionalidades;
- estructura preparada para integracion futura con Retrofit y DTOs.

No se implementa un backend real porque el enunciado indica que no es requerido para esta PoC.

## Estructura del repositorio

```text
/app        Proyecto Android con Jetpack Compose
/contracts  Contratos de API en YAML
/docs       Documentacion tecnica y decisiones arquitectonicas
/video      Archivo con enlace al video demo
README.md   Descripcion principal del proyecto
```

## Tecnologias

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- MVVM
- Coroutines
- StateFlow
- Retrofit preparado para integracion futura
- Mock Data para la PoC

## Como ejecutar

1. Abrir la carpeta `app` en Android Studio.
2. Esperar la sincronizacion de Gradle.
3. Ejecutar la configuracion `app` en un emulador o dispositivo Android.

La aplicacion debe funcionar con datos simulados, sin depender de un backend externo.

## Decisiones importantes

- Se usa MVVM para separar UI, estado y acceso a datos.
- Se usa Mock Data porque el alcance de la PoC no incluye un backend funcional.
- Se deja Retrofit y DTOs preparados para que una API real pueda integrarse despues.
- Se usa StateFlow para que el listado reaccione automaticamente cuando se crea un ticket o cambia su prioridad.
- Se usan Feature Flags locales para controlar funcionalidades del MVP sin tocar multiples pantallas.
- Se evita sobreingenieria: no se agrega DI avanzado, base de datos local, backend falso complejo ni patrones empresariales innecesarios.

## Documentacion adicional

- `docs/technical-justification.md`
- `docs/architecture-decisions.md`
- `docs/event-driven-strategy.md`
- `docs/feature-flags.md`
- `docs/system-flow.md`
- `contracts/tickets-api.yaml`
- `video/demo-link.md`

