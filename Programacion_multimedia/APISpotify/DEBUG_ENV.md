# Guía de Debug: Credenciales no se cargan

Si `clientId` y `clientSecret` siguen siendo vacíos (`""`), sigue estos pasos:

## 1. Verifica que el archivo `.env` existe en el lugar correcto

```bash
# Desde la raíz del proyecto:
ls -la app/src/main/assets/.env
```

Debería mostrar algo como:
```
-rw-r--r--  1 user  group  200 Feb 24 10:30 .env
```

Si no existe, crea la carpeta y el archivo:
```bash
mkdir -p app/src/main/assets
cat > app/src/main/assets/.env << 'EOF'
SPOTIFY_CLIENT_ID=14701403be7e4853aa9e96d79ea89ff9
SPOTIFY_CLIENT_SECRET=7173639ffba045d79e68a7a9028476e2
EOF
```

## 2. Verifica el contenido del archivo `.env`

```bash
cat app/src/main/assets/.env
```

Debe verse así (SIN espacios alrededor del `=`):
```
SPOTIFY_CLIENT_ID=14701403be7e4853aa9e96d79ea89ff9
SPOTIFY_CLIENT_SECRET=7173639ffba045d79e68a7a9028476e2
```

❌ INCORRECTO:
```
SPOTIFY_CLIENT_ID = 14701403be7e4853aa9e96d79ea89ff9
SPOTIFY_CLIENT_SECRET = 7173639ffba045d79e68a7a9028476e2
```

## 3. Reconstruye la app completamente

En Android Studio:
1. `Build` → `Clean Project`
2. `Build` → `Rebuild Project`
3. `Run` → `Run 'app'`

Desde terminal:
```bash
./gradlew clean build
```

## 4. Revisa los logs en Logcat

En Android Studio, abre `Logcat` (abajo) y busca `EnvConfig`:

```
D/EnvConfig: Iniciando carga de .env...
D/EnvConfig: Intentando leer desde assets/.env...
D/EnvConfig: Contenido de assets/.env: SPOTIFY_CLIENT_ID=14701...
D/EnvConfig: Parseando contenido (.env)...
D/EnvConfig: Clave: 'SPOTIFY_CLIENT_ID' -> Valor: '14701...'
D/EnvConfig: ✓ SPOTIFY_CLIENT_ID asignado: 14701403be7e4853aa9e96d79ea89ff9
D/EnvConfig: ✓ Cargado desde assets/. clientId=14701..., clientSecret=7173...
```

Si ves "✓ Cargado" en los logs pero aún así `clientId` es vacío en pantalla, hay un problema de sincronización. Intenta:

## 5. Problema de sincronización (si aún está vacío)

Añade un pequeño delay antes de usar las credenciales en `MainActivity.kt`:

```kotlin
// En onCreate, después de loadEnv:
Thread.sleep(500)  // Espera 500ms
clientId = EnvConfig.getClientId()
clientSecret = EnvConfig.getClientSecret()
```

## 6. Alternativa: Hardcodear valores para test

Temporalmente, en `MainActivity.kt` reemplaza:
```kotlin
private lateinit var clientId: String
private lateinit var clientSecret: String
```

Por:
```kotlin
private var clientId = "14701403be7e4853aa9e96d79ea89ff9"  // Para test
private var clientSecret = "7173639ffba045d79e68a7a9028476e2"  // Para test
```

Si funciona así, el problema está 100% en la lectura de `.env`.

## 7. Checklist final

- [ ] `app/src/main/assets/.env` existe
- [ ] El archivo contiene `SPOTIFY_CLIENT_ID=...` (sin espacios)
- [ ] El archivo contiene `SPOTIFY_CLIENT_SECRET=...` (sin espacios)
- [ ] Hiciste `Clean Project` y `Rebuild Project`
- [ ] Reinstalaste la app en el emulador/dispositivo
- [ ] Revisaste Logcat y ves los mensajes de `EnvConfig`

Si aún así no funciona, comparte:
1. El contenido exacto de `app/src/main/assets/.env`
2. Los logs completos de `EnvConfig` desde Logcat
3. La versión de Android (API level)
