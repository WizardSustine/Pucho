# Diseño de la Aplicación "Dejar de Fumar"

Este documento describe las decisiones de diseño a nivel de componentes de software y patrones utilizados en la aplicación.

## Patrones de Diseño Utilizados

1.  **MVVM (Model-View-ViewModel):**
    *   **View (Vista):** La `Activity` o `Fragment`. Es responsable de la presentación visual y de capturar la entrada del usuario. Observa el `ViewModel`.
    *   **ViewModel (Modelo de Vista):** `MainViewModel`. Proporciona datos a la `View` a través de `LiveData` y expone métodos para que la `View` pueda pasarle las acciones del usuario. No tiene conocimiento directo de la `View`.
    *   **Model (Modelo):** Representado por la capa de datos (`AlarmAndBDController` y las entidades como `PuchoDia`). Contiene la lógica de negocio y el acceso a los datos.

2.  **Patrón Observador (Observer Pattern):**
    *   Implementado a través de `LiveData`. La UI (`Observer`) se suscribe a los cambios en los datos (`Observable`, que es el `LiveData` dentro del `ViewModel`). Cuando los datos se modifican, la UI es notificada y se redibuja a sí misma sin que el `ViewModel` necesite una referencia a ella. Esto previene fugas de memoria y maneja automáticamente el ciclo de vida de la UI.

3.  **Patrón Repositorio (Repository Pattern):**
    *   Aunque no está explícitamente nombrado como "Repository", la clase `AlarmAndBDController` actúa como tal. Centraliza el acceso a múltiples fuentes de datos (base de datos, alarmas del sistema) y abstrae esta complejidad del `ViewModel`. El `ViewModel` solo pide los datos sin saber si vienen de una base de datos local, una API remota o la memoria caché.

4.  **Inyección de Dependencias (Manual):**
    *   El `Context` de la aplicación se "inyecta" en `AlarmAndBDController` a través del constructor de `MainViewModel`. Esto hace que el controlador sea más fácil de probar, ya que se le podría pasar un contexto de prueba. Para proyectos más grandes, se podría considerar el uso de una librería como Hilt o Koin para automatizar este proceso.

## Diseño de Componentes Clave

*   **`MainViewModel`:**
    *   Diseñado para ser el único punto de verdad para la UI.
    *   Utiliza `MutableLiveData` internamente para poder modificar los datos, pero los expone como `LiveData` (inmutable) para que la UI solo pueda observarlos, no modificarlos directamente. Esto refuerza un flujo de datos unidireccional.
    *   Hereda de `AndroidViewModel` para poder acceder al `ApplicationContext`, necesario para inicializar el `AlarmAndBDController` sin arriesgarse a fugas de memoria asociadas con el contexto de una `Activity`.

*   **`AlarmAndBDController`:**
    *   Diseñado como una clase de utilidad o controlador central. Agrupa responsabilidades relacionadas con la persistencia y las tareas en segundo plano (alarmas).
    *   Esta centralización simplifica la lógica en el `ViewModel`, que solo necesita delegar las tareas.
    *   Su nombre sugiere que maneja tanto alarmas como la base de datos, lo cual es una decisión de diseño para mantener la cohesión en un proyecto de tamaño pequeño a mediano.

*   **`PuchoDia` (Entidad):**
    *   Una clase de datos simple (POJO) que representa el modelo de dominio. Contiene los atributos de un día de consumo: consumo, expectativa, fecha, etc. Es fundamental para la comunicación de datos entre las capas.
