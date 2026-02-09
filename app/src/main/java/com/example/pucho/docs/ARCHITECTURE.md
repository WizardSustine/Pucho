# Arquitectura de la Aplicación "Dejar de Fumar"

La aplicación sigue los principios de la **Arquitectura Limpia (Clean Architecture)** adaptada a Android, promoviendo una separación clara de responsabilidades, lo que facilita el mantenimiento, la escalabilidad y las pruebas. La arquitectura se basa principalmente en los componentes de Android Jetpack.

## Componentes Principales

La arquitectura se divide en tres capas principales:

1.  **Capa de UI (Presentación):**
    *   **Responsabilidad:** Mostrar los datos en la pantalla y capturar las interacciones del usuario.
    *   **Componentes:** `Activities`, `Fragments`, `Adapters` (para `RecyclerView` o `ListView`).
    *   **Funcionamiento:** La UI observa los `LiveData` expuestos por el `ViewModel`. Cuando los datos cambian, la UI se actualiza automáticamente. Las interacciones del usuario (como hacer clic en un botón) invocan métodos en el `ViewModel`. Esta capa no contiene lógica de negocio.

2.  **Capa de ViewModel (Lógica de Presentación):**
    *   **Responsabilidad:** Actuar como intermediario entre la UI y la capa de datos. Prepara y gestiona los datos para la UI.
    *   **Componentes:** `MainViewModel` (que hereda de `AndroidViewModel`).
    *   **Funcionamiento:** `MainViewModel` mantiene el estado de la UI y lo expone a través de `LiveData` (`dailyDataModel`, `graphDataModel`). No interactúa directamente con las fuentes de datos (base de datos, red), sino que delega esa responsabilidad al `AlarmAndBDController`. Es independiente de los detalles de la UI (no tiene referencias a `Activities` o `Fragments`).

3.  **Capa de Datos y Lógica de Negocio (Dominio/Datos):**
    *   **Responsabilidad:** Gestionar la lógica de negocio central, las fuentes de datos (base de datos, SharedPreferences) y las interacciones con el sistema operativo (como `AlarmManager`).
    *   **Componentes:** `AlarmAndBDController`, `PuchoDia` (Entidad).
    *   **Funcionamiento:** `AlarmAndBDController` es el "cerebro" de la aplicación. Centraliza todas las operaciones:
        *   Consultas y modificaciones en la base de datos (ej. `get30Dias`, `addPucho`).
        *   Gestión de alarmas (`setAlarmEvent`).
        *   Manejo de notificaciones (`closeNotification`).
        *   Cálculos de negocio (ej. `setExpectativas`).

## Blueprint de la Arquitectura

A continuación se muestra un diagrama que ilustra el flujo de datos y las dependencias entre los componentes.







**Flujo de Datos:**

1.  **Carga de Datos:** `Activity/Fragment` -> `MainViewModel` -> `AlarmAndBDController` -> `Base de Datos`. Los datos retornan como `LiveData`.
2.  **Actualización de UI:** `Base de Datos` -> `AlarmAndBDController` -> `MainViewModel` actualiza `LiveData` -> `Activity/Fragment` se actualiza automáticamente.
3.  **Interacción del Usuario:** `Activity/Fragment` -> `MainViewModel` -> `AlarmAndBDController` -> `Base de Datos` / `AlarmManager`.

Esta arquitectura desacoplada permite que `AlarmAndBDController` pueda ser reutilizado o reemplazado sin afectar el `ViewModel` o la UI, y viceversa.
