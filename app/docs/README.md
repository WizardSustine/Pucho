# Dejar de Fumar - Aplicación Android

"Dejar de Fumar" es una aplicación para Android diseñada para ayudar a los usuarios a monitorear y reducir su consumo de cigarrillos. La aplicación permite llevar un registro diario del consumo, establecer metas y visualizar el progreso a lo largo del tiempo.

## Características Principales

*   **Registro Diario:** Permite al usuario registrar cada cigarrillo consumido con un solo toque.
*   **Metas y Expectativas:** Los usuarios pueden definir una expectativa de consumo diario para ayudar a controlar el hábito.
*   **Visualización de Progreso:** Un gráfico muestra el historial de consumo de los últimos 30 días, permitiendo al usuario ver su progreso.
*   **Historial Detallado:** Muestra una lista con el consumo de días anteriores.
*   **Notificaciones y Alarmas:** El sistema puede generar recordatorios o notificaciones para ayudar al usuario en su proceso.

## Estructura del Proyecto

El proyecto sigue una arquitectura moderna de Android basada en los componentes de **Android Jetpack**.

*   **UI Layer (Capa de Interfaz de Usuario):** Compuesta por Activities y Fragments que muestran la información al usuario.
*   **ViewModel:** `MainViewModel` actúa como intermediario entre la UI y la lógica de negocio, exponiendo los datos a través de `LiveData`.
*   **Domain/Data Layer (Capa de Lógica/Datos):** `AlarmAndBDController` gestiona la lógica de negocio, incluyendo el acceso a la base de datos (probablemente SQLite/Room) y la gestión de alarmas (`AlarmManager`).
*   **Entidades:** Clases de datos como `PuchoDia` que modelan la información que maneja la aplicación.

## Cómo Empezar

1.  Clona el repositorio.
2.  Abre el proyecto en Android Studio.
3.  Compila y ejecuta la aplicación en un emulador o dispositivo físico.

## Dependencias Clave

*   **Android Jetpack:**
    *   `ViewModel`: Para gestionar los datos de la UI de forma consciente del ciclo de vida.
    *   `LiveData`: Para notificar a la UI sobre cambios en los datos de forma reactiva.
*   **Otras dependencias estándar de AndroidX.**

## Para ver más documentación
* **Arquitectura** --> [ARCHITECTURE.md](https://github.com/WizardSustine/Pucho/edit/master/app/docs/ARCHITECTURE.md)
* **Diseño** --> [DESIGN.md](https://github.com/WizardSustine/Pucho/blob/master/app/docs/DESIGN.md)
