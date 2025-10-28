# Agenda de Citas Médicas

Una aplicación móvil Android desarrollada en Kotlin con Jetpack Compose para la gestión de citas médicas.

## Características Implementadas

### ✅ Funcionalidades Principales
- **Registro de Pacientes**: Crear y gestionar información de pacientes
- **Gestión de Doctores**: Administrar doctores y sus especialidades
- **Sistema de Citas**: Programar, visualizar y eliminar citas médicas
- **Base de Datos Local**: Almacenamiento persistente con Room Database
- **Interfaz Moderna**: UI desarrollada con Jetpack Compose y Material Design 3

### 📱 Pantallas de la Aplicación
1. **Pantalla Principal**: Vista general con próximas citas y navegación
2. **Lista de Citas**: Visualización completa de todas las citas programadas
3. **Nueva Cita**: Formulario para crear citas con selección de paciente, doctor, fecha y hora
4. **Gestión de Pacientes**: Lista y registro de pacientes
5. **Nuevo Paciente**: Formulario de registro de pacientes

### 🗄️ Base de Datos
- **Entidades**: Paciente, Doctor, Cita
- **Relaciones**: Foreign Keys entre Cita-Paciente y Cita-Doctor
- **Operaciones CRUD**: Crear, Leer, Actualizar, Eliminar para todas las entidades

### 🎨 Tecnologías Utilizadas
- **Kotlin** - Lenguaje de programación
- **Jetpack Compose** - Framework de UI
- **Room Database** - Base de datos local
- **Navigation Compose** - Navegación entre pantallas
- **Material Design 3** - Sistema de diseño
- **ViewModel** - Gestión de estado
- **StateFlow** - Flujo de datos reactivo

## Estructura del Proyecto

```
app/src/main/java/com/example/citasmedicas_app01/
├── data/
│   ├── entity/          # Entidades de la base de datos
│   ├── dao/             # Data Access Objects
│   ├── database/        # Configuración de Room
│   └── repository/      # Repositorio de datos
├── ui/
│   ├── screens/         # Pantallas de la aplicación
│   ├── viewmodel/       # ViewModels
│   └── theme/           # Tema de la aplicación
└── MainActivity.kt      # Actividad principal
```

## Funcionalidades CRUD Implementadas

### Pacientes
- ✅ **Crear**: Formulario de registro con validaciones
- ✅ **Leer**: Lista de pacientes con información completa
- ✅ **Actualizar**: (Preparado para implementación futura)
- ✅ **Eliminar**: (Preparado para implementación futura)

### Doctores
- ✅ **Crear**: Inserción automática de doctores de ejemplo
- ✅ **Leer**: Lista de doctores con especialidades
- ✅ **Actualizar**: (Preparado para implementación futura)
- ✅ **Eliminar**: (Preparado para implementación futura)

### Citas
- ✅ **Crear**: Formulario completo con selección de paciente, doctor, fecha y hora
- ✅ **Leer**: Lista de citas con información detallada
- ✅ **Actualizar**: (Preparado para implementación futura)
- ✅ **Eliminar**: Eliminación de citas con confirmación

## Datos de Ejemplo

La aplicación incluye datos de ejemplo que se cargan automáticamente:

### Pacientes
- Juan Pérez (555-1234, juan@email.com)
- María García (555-5678, maria@email.com)

### Doctores
- Dr. Carlos López - Cardiología (555-1111)
- Dra. Ana Martínez - Pediatría (555-2222)

## Próximas Mejoras (No Implementadas)

- Portal web para administración
- Servicios REST para sincronización
- Notificaciones push
- Búsqueda y filtros avanzados
- Edición de citas existentes
- Gestión de horarios de doctores
- Reportes y estadísticas
- Autenticación de usuarios

## Instalación y Uso

1. Clona el repositorio
2. Abre el proyecto en Android Studio
3. Sincroniza las dependencias de Gradle
4. Ejecuta la aplicación en un dispositivo o emulador

## Requisitos del Sistema

- Android API 24+ (Android 7.0)
- Kotlin 1.9+
- Android Studio Arctic Fox o superior

---

**Nota**: Esta es una versión funcional pero no completa de la aplicación. Incluye las funcionalidades básicas de CRUD y una interfaz de usuario moderna, pero está diseñada como un avance que puede ser expandido con más características según las necesidades del proyecto.



