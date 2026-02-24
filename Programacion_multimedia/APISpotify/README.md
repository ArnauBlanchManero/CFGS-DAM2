# API Spotify - Búsqueda de Canciones

Aplicación Android que permite buscar canciones en Spotify y mostrar información detallada sobre ellas.

## 🚀 Características

- ✅ Búsqueda de canciones en tiempo real
- ✅ Muestra: nombre, artista, álbum, duración, popularidad, fecha de lanzamiento
- ✅ Indicador de contenido explícito
- ✅ Autenticación automática con API de Spotify
- ✅ Credenciales seguras (nunca en GitHub)

## 📋 Requisitos

- Android API 24+
- Conexión a Internet
- Credenciales de Spotify (Client ID y Client Secret)

## ⚙️ Configuración (Obligatorio)

### 1. Obtener credenciales de Spotify

1. Ve a https://developer.spotify.com/dashboard
2. Registrate/inicia sesión
3. Crea una nueva app (o usa una existente)
4. Copia tu **Client ID** y **Client Secret**

### 2. Configurar la app

Edita el archivo `app/src/main/res/raw/env`:

```
SPOTIFY_CLIENT_ID=tu_client_id_aqui
SPOTIFY_CLIENT_SECRET=tu_client_secret_aqui
```

Reemplaza los valores con tus credenciales reales.

### 3. Compilar y ejecutar

En Android Studio:
1. `Build` → `Clean Project`
2. `Build` → `Rebuild Project`
3. `Run` → `Run 'app'`

O desde terminal:
```bash
./gradlew clean build
./gradlew installDebug  # Para instalar en emulador
```

## 🔒 Seguridad

- Las credenciales se guardan en `app/src/main/res/raw/env` (local, nunca se sube a GitHub)
- El archivo `.env` en la raíz es para desarrollo local (ignorado por Git)
- Usa `EnvConfig.kt` para cargar credenciales de forma segura
- Los logs de debug se han minimizado en producción

## 📁 Estructura de archivos

```
APISpotify/
├── .env                                    # Credenciales locales (NO en Git)
├── .gitignore                              # Ignora .env automáticamente
├── app/
│   └── src/main/
│       ├── java/com/example/apispotify/
│       │   ├── MainActivity.kt             # Actividad principal
│       │   ├── Artist.kt                   # Modelos de datos
│       │   ├── SpotifyService.kt           # Interfaz Retrofit
│       │   └── EnvConfig.kt                # Cargador de credenciales
│       ├── res/
│       │   ├── layout/activity_main.xml    # UI
│       │   └── raw/env                     # Credenciales (placeholders)
│       └── AndroidManifest.xml
└── build.gradle.kts
```

## 🛠️ Cómo funciona

1. **Carga de credenciales** (`EnvConfig.kt`):
   - Intenta leer desde `res/raw/env` (recomendado)
   - Fallback: `assets/.env`, `files/.env`, `externalFiles/.env`

2. **Autenticación** (`MainActivity.kt`):
   - Obtiene token de Spotify automáticamente al iniciar
   - Usa `SpotifyAuthService` para autenticarse

3. **Búsqueda** (`SpotifyService.kt`):
   - Usa el token para buscar canciones
   - Retorna resultados con información detallada

## 🐛 Solución de problemas

| Problema | Solución |
|----------|----------|
| "Credenciales no configuradas" | Edita `app/src/main/res/raw/env` con tus valores |
| "Error: 401/403 No autorizado" | Verifica que Client ID y Secret sean correctos |
| "Error de conexión" | Comprueba que tienes internet activado |
| "No se encontró ninguna canción" | Intenta con nombres más comunes |

## 📚 Tecnologías

- **Lenguaje**: Kotlin
- **HTTP**: Retrofit 2.9.0
- **JSON**: Gson
- **API**: Spotify Web API

## 📖 Para otros desarrolladores

Si clonas este repo:

1. Edita `app/src/main/res/raw/env`
2. Pon tus credenciales de Spotify
3. Compila: `./gradlew clean build`
4. ¡Listo!

Las credenciales nunca se suben a GitHub gracias a `.gitignore`.

## 📝 Licencia

Proyecto educativo para la asignatura de Programación Multimedia (CFGS DAM2)

