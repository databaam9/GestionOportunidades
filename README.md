# Gestión de Oportunidades

## Integrantes

- Joshua Rios
- Carlos Colonia

## Funcionalidades implementadas

- Inicio de sesión con validación de credenciales.
- Menú lateral de navegación (Drawer) con acceso a los módulos de la aplicación.
- Listado de oportunidades persistido localmente con Room, actualizado en tiempo real.
- Formulario de creación y edición de oportunidades con validación por campo e íconos descriptivos.
- Escaneo de código QR con CameraX y ML Kit para completar automáticamente el campo Fuente.
- Adjuntar evidencia fotográfica desde la galería del dispositivo.
- Eliminación de oportunidades con confirmación.
- Animaciones de transición en el formulario, la lista de oportunidades y los mensajes de error.
- Persistencia local con Room (sobrevive al cierre y reinicio de la aplicación).

## Arquitectura

El proyecto sigue el patrón MVVM:

- `data/local`: entidades Room, DAO y base de datos.
- `data/repository`: repositorios que exponen los datos a los ViewModel.
- `ui/login`: pantalla e inicio de sesión.
- `ui/oportunidad`: listado, formulario y lógica de oportunidades.
- `ui/navegacion`: definición de rutas con Navigation Compose.
- `ui/utils`: utilidades de cámara y escaneo de QR.
- `view`: menú lateral de navegación.

## Recursos nativos utilizados

1. Cámara del dispositivo (CameraX + ML Kit) para escanear códigos QR.
2. Galería del dispositivo (selector de fotos del sistema) para adjuntar evidencia fotográfica.
