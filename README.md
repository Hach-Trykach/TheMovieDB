# The Movie DB App

Para poder ejecutar la aplicación de manera local, se necesita un obtener un [API Key](https://www.themoviedb.org/settings/api) para poder acceder a la api de películas. Una vez se cuente con el API Key se tiene que reemplazar `${System.getenv("THE_MOVIE_DB_API_KEY")}` en la sigueinte linea en `app/build.gradle`:

```groovy
buildConfigField("String", "THE_MOVIE_DB_API_KEY", "\"${System.getenv("THE_MOVIE_DB_API_KEY")}\"")
```

o bien, agregar el token como variable de entorno como:

```bash
export THE_MOVIE_DB_API_KEY=PUT_YOUR_TOKEN_HERE
```

#### Capas de la aplicación

La aplicación cuenta de tres modulos principales.

1. **api** (libreria java): Módulo encargado de acceder al api de [The Movie DB](https://www.themoviedb.org/) y es el responsable de la capa de networking. Contiene modelos de networking con el prefijo `Api*` como `ApiMovie`.
2. **db** (libreria android): Módulo encargado de la persistencia y carga de datos localmente para el cache de los datos y poder soportar el modo offline. Contiene entities de base de datos(sqlite) con el prefijo `Db*` como `DbMovie`.
3. **app**: Módulo de la aplicación principal que hace uso de los modulos anteriores para poder acceder a los datos a través del `MoviesRepository`. Éste módulo es el responsable de la capa de presentacio a través de una arquitectura MVVM. Contiene modelos de presentacion con el prefijo `Ui*` como `UiMovie`.

#### Arquitectura

El módulo **app** hace uso de una arquitectura MVVM para la capa depresentacion la cual se encuenta definida en el `package dev.epool.themoviedb.app.ui.screens.*`, cada pantalla esta definida en su respectivo `package` con sus respectivas clases:

1. `Activity/Fragment`: Actua como vista, es encargado de escuchar por cambios en el estado de la vista en conjunto con su respectiva clase `*ViewState` para la presentación del estado actual de la vista. Por ejemplo `MoviesFragment`.
2. `ViewModel`: Actua de intermediario entre el repositorio de datos y la capa de vista. Es engardo de exponer funcionalidad a la vista, de emitir eventos hacia la vista y también es responable de la capa de negocio. Por ejemplo `MoviesViewModel`.
3. `Repository`: Responsable del acceso local y remoto de datos. Por ejemplo `MoviesRepository`.

#### Preguntas

1. En qué consiste el principio de responsabilidad única? Cuál es su propósito?

> Consiste en que cada componente(capa, clase, etc.) deber asumir una unica responsabilidad y no tener mas de una. En caso de se tenga mas de una resposabiidad se debe considerar extraer la responsabilidad extra en otro componente.

> El proposito es evitar el acomplamiento de componente y hacerlos mas modulares, de esta manera se puede agregar y remover funcionalidad de manera fácil. Esto ayuda mucho en conjunto con la inyección de dependecias y a hacer pruebas unitarias de manera mas fácil al mockear componentes para aislar el caso de prueba. 

2. Qué características tiene, según su opinión, un "buen" código o código limpio?

> Un buen código es fácil de leer y entender, por lo tanto se reduce tiempos y costos al mantener un código base por otros desarrolladores. También es escrito teniendo en cuenta la eficiencia del mismo para evitar el uso de recursos inecesarios y evitar leaks de memoria.

#### Lint y Tests

Para ejecutar las pruebas unitarias y de integración, se puede hacer con los siguientes comandos.

1. **api**: `./gradlew :api:check`
2. **db**: `./gradlew :db:connectedAndroidTest` (Necesita estar conectado un emulador o telefono en modo debug a la computadora).
3. **app**: `./gradlew :app:check`