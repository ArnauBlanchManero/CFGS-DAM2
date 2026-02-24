# Aplicación de Búsqueda de Canciones Spotify

## ¿Qué hace esta app?

Una aplicación Android sencilla que permite:
1. Escribir el nombre de una canción
2. Buscar en la API de Spotify
3. Mostrar información sobre la canción (artista, álbum, duración, popularidad, etc.)

## Requisitos: Obtener tu Token de Spotify

**IMPORTANTE:** Necesitas un token válido de Spotify para que funcione.

### Pasos para obtener el token:

1. **Regístrate en Spotify Developer:**
   - Ve a https://developer.spotify.com/dashboard
   - Si no tienes cuenta, crea una (es gratis)
   - Log-in con tu cuenta de Spotify

2. **Crear una aplicación:**
   - En el dashboard, haz clic en "Create an App"
   - Acepta los términos
   - Pon un nombre a tu app (ej: "APISpotifyApp")

3. **Obtener el token temporal:**
   - Ve a https://developer.spotify.com/console
   - En la derecha, verás un botón "Get Token"
   - Haz clic y selecciona "Coloquios de usuario" o cualquier opción disponible
   - Copia el token que aparece (empieza con muchos caracteres)

### Cómo usar el token:

1. Abre `MainActivity.kt`
2. Busca esta línea:
   ```kotlin
   private val myToken = "Bearer TU_ACCESS_TOKEN_AQUI"
   ```
3. Reemplaza `TU_ACCESS_TOKEN_AQUI` con el token que obtuviste
4. El resultado debe verse así (ejemplo):
   ```kotlin
   private val myToken = "Bearer BQC1234567890abcdefgh..."
   ```

## Estructura del código:

- **Artist.kt**: Modelos de datos (Track, Album, Artist, etc.)
- **SpotifyService.kt**: Interfaz de Retrofit para hacer las peticiones
- **MainActivity.kt**: La actividad principal con la búsqueda y lógica
- **activity_main.xml**: Layout de la UI

## Cómo usar la app:

1. Instala la app en tu emulador o teléfono
2. Escribe el nombre de una canción (ej: "Bohemian Rhapsody")
3. Haz clic en "Buscar"
4. Espera unos segundos y verás la información de la canción

## ¡Listo!

¡Tu app debería funcionar! Si tienes problemas, verifica:
- ✓ El token está correcto y tiene "Bearer " al principio
- ✓ Tienes permiso de INTERNET en AndroidManifest.xml
- ✓ Tu teléfono/emulador tiene conexión a internet
