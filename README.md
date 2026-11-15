# Diario espacial — PC02 DSM 2026-2

Aplicación Android (Kotlin + Jetpack Compose) que consume la API abierta de la NASA (APOD y NeoWs) y guarda los datos en SQLite con Room.

## Integrantes

| Integrante | Opción | Rama |
|---|---|---|
| Hernández (23200334) | Alumno 1 — Imágenes (APOD) | `hernandez` |
| Navarro (19200255) | Alumno 2 — Asteroides (NeoWs) | `navarro` |

## Acuerdo de coordinación

**Base común:** la prepara Navarro en la rama `navarro` y se integra a `main` con el primer Pull Request, revisado y aprobado por Hernández. Incluye:

- Dependencias: Retrofit + Gson, Room (KSP), ViewModel para Compose, Navigation Compose y Coil.
- `data/remote/RetrofitClient`: dos instancias de Retrofit, `apod` (`https://science.nasa.gov/`) y `neoWs` (`https://api.nasa.gov/`). El parámetro `api_key` se agrega automáticamente a todas las peticiones.
- `data/local/AppDatabase`: base de datos con las tablas `imagenes` y `asteroides`. Cada tabla se crea en versión mínima (solo la clave primaria) y su DAO vacío.
- `navigation/AppNavigation`: barra inferior con Imágenes y Asteroides. Cada opción tiene su propio grafo (`imagenesGraph` y `asteroidesGraph`) con una pantalla provisional.

**Cómo se integra:** cuando la base común esté en `main`, cada integrante la trae a su rama con `git merge main` y trabaja solo dentro del paquete de su opción.

**Reglas para evitar conflictos:**

- Cada integrante modifica solo su paquete: `imagenes/` (Hernández) o `asteroides/` (Navarro). Ahí van su API, DTOs, Entity, DAO, Repository, ViewModel, pantallas y navegación interna.
- Los archivos comunes (`data/`, `navigation/`, `build.gradle.kts`, `libs.versions.toml`, `AndroidManifest.xml`) no se modifican en las ramas de opción. Si hace falta un cambio común, se acuerda antes y lo hace una sola persona.
- Si un integrante necesita otra dependencia, la agrega avisando al otro para no editar la misma línea a la vez.
- Al agregar columnas a una Entity, se desinstala la app del emulador antes de probar (la base está en versión 1 durante el desarrollo).
- Los campos de los DTOs se declaran como nulables para que la app no se cierre si la API devuelve datos incompletos.

**Flujo de trabajo:** `main` está protegida. Cada integrante trabaja en su rama, hace commits pequeños con mensajes que explican el cambio y entrega su parte con un Pull Request que el otro revisa, comenta y aprueba.

## Configuración de la API key

Cada integrante usa su propia clave de https://api.nasa.gov. Se agrega en `local.properties` (no se sube al repositorio):

```properties
NASA_API_KEY=tu_clave
```

En el código se lee como `BuildConfig.NASA_API_KEY`.

## Requisitos

- Android Studio reciente (el proyecto usa JDK 25 para Gradle, que Android Studio trae incluido).
- minSdk 33 · targetSdk 37.
