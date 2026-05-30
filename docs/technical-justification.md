# Justificacion tecnica

## Contexto

Panini necesita una prueba de concepto movil para centralizar tickets de soporte relacionados con proveedores, inventario, distribucion y problemas logisticos del album del Mundial 2026.

El proyecto no busca simular una plataforma empresarial completa. El objetivo es demostrar una base movil clara, funcional y facil de continuar por otros ingenieros.

## Por que Jetpack Compose

Jetpack Compose permite construir pantallas de forma declarativa y directa. Para una PoC de corto alcance es una buena eleccion porque reduce codigo repetitivo, facilita la lectura de la UI y se integra naturalmente con estados observables como `StateFlow`.

## Por que MVVM

MVVM se eligio porque separa responsabilidades sin agregar demasiada complejidad:

- la pantalla Compose solo renderiza estado y envia acciones;
- el ViewModel mantiene el estado de UI y coordina operaciones;
- el Repository entrega datos, primero desde mocks y luego desde una API real;
- los modelos y DTOs quedan separados para facilitar mantenimiento.

Esta separacion permite que otro desarrollador cambie la fuente de datos sin reescribir las pantallas.

## Por que Mock Data

El enunciado indica que no se requiere backend funcional. Por eso la aplicacion debe funcionar con datos simulados.

Los mocks representan casos realistas del negocio:

- faltantes de inventario;
- retrasos de distribucion;
- incidentes con proveedores;
- problemas logisticos;
- tickets con prioridad y estado.

Esto permite validar la experiencia movil y la arquitectura sin gastar tiempo en infraestructura fuera del alcance.

## Por que StateFlow

`StateFlow` permite exponer estado observable desde ViewModels y repositorios. Es suficiente para los escenarios pedidos:

- al crear un ticket, el listado se actualiza automaticamente;
- al cambiar una prioridad, la lista se reordena sin recargar la pantalla;
- Compose recompone la UI cuando cambia el estado.

No se usa un bus de eventos complejo porque seria excesivo para la PoC.

## Por que Feature Flags

Los Feature Flags permiten activar o desactivar funcionalidades en pruebas internas sin modificar varias pantallas.

Para este MVP basta con flags locales, por ejemplo:

- habilitar creacion de tickets;
- habilitar actualizacion de prioridad.

Mas adelante podrian venir desde Remote Config o desde un backend, pero para esta PoC una solucion local es mas simple y defendible.

## Preparacion para integracion futura

Aunque la app funciona con mocks, se documenta y prepara una estructura compatible con Retrofit:

- contratos YAML en `/contracts`;
- DTOs para requests y responses;
- Repository como frontera entre UI y datos;
- rutas y modelos consistentes con un backend futuro.

Cuando exista backend, se puede reemplazar la fuente mock del Repository por llamadas Retrofit sin cambiar el flujo principal de UI.

## Como se evita la sobreingenieria

Se evita incluir:

- backend falso innecesario;
- base de datos local si no es requerida;
- inyeccion de dependencias avanzada;
- multiples capas abstractas sin uso real;
- patrones dificiles de explicar en el video.

La solucion prioriza claridad, continuidad y cumplimiento directo del enunciado.

