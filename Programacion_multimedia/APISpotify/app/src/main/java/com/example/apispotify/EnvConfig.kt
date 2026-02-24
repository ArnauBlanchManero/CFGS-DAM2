package com.example.apispotify

import android.content.Context
import android.util.Log
import java.io.File

object EnvConfig {
    private var clientId: String = ""
    private var clientSecret: String = ""
    private var isLoaded = false
    private const val TAG = "EnvConfig"

    fun loadEnv(context: Context) {
        if (isLoaded) return

        Log.d(TAG, "Cargando configuración de credenciales...")

        // Intento 1: Leer desde res/raw/env (RECOMENDADO)
        if (tryLoadFromRaw(context)) {
            Log.d(TAG, "✓ Credenciales cargadas correctamente")
            isLoaded = true
            return
        }

        // Intento 2: Leer desde assets/
        if (tryLoadFromAssets(context)) {
            Log.d(TAG, "✓ Credenciales cargadas correctamente")
            isLoaded = true
            return
        }

        // Intento 3: Leer desde files dir
        if (tryLoadFromFilesDir(context)) {
            Log.d(TAG, "✓ Credenciales cargadas correctamente")
            isLoaded = true
            return
        }

        // Intento 4: Leer desde external files dir
        if (tryLoadFromExternalFilesDir(context)) {
            Log.d(TAG, "✓ Credenciales cargadas correctamente")
            isLoaded = true
            return
        }

        Log.e(TAG, "❌ Error: No se pudieron cargar las credenciales de Spotify")
    }

    private fun tryLoadFromRaw(context: Context): Boolean {
        return try {
            val inputStream = context.resources.openRawResource(R.raw.env)
            val content = inputStream.bufferedReader().use { it.readText() }
            parseEnv(content)
            clientId.isNotEmpty() && clientSecret.isNotEmpty()
        } catch (e: Exception) {
            Log.w(TAG, "res/raw/env no disponible")
            false
        }
    }

    private fun tryLoadFromAssets(context: Context): Boolean {
        return try {
            val envFile = context.assets.open(".env")
            val content = envFile.bufferedReader().use { it.readText() }
            parseEnv(content)
            clientId.isNotEmpty() && clientSecret.isNotEmpty()
        } catch (e: Exception) {
            Log.w(TAG, "assets/.env no disponible")
            false
        }
    }

    private fun tryLoadFromFilesDir(context: Context): Boolean {
        return try {
            val file = File(context.filesDir, ".env")
            if (file.exists()) {
                val content = file.readText()
                parseEnv(content)
                clientId.isNotEmpty() && clientSecret.isNotEmpty()
            } else {
                false
            }
        } catch (e: Exception) {
            Log.w(TAG, "Error leyendo desde files dir")
            false
        }
    }

    private fun tryLoadFromExternalFilesDir(context: Context): Boolean {
        return try {
            val externalDir = context.getExternalFilesDir(null)
            if (externalDir != null) {
                val file = File(externalDir, ".env")
                if (file.exists()) {
                    val content = file.readText()
                    parseEnv(content)
                    clientId.isNotEmpty() && clientSecret.isNotEmpty()
                } else {
                    false
                }
            } else {
                false
            }
        } catch (e: Exception) {
            Log.w(TAG, "Error leyendo desde external files dir")
            false
        }
    }

    private fun parseEnv(content: String) {
        Log.d(TAG, "Parseando configuración...")

        content.lines().forEach { line ->
            val trimmed = line.trim()

            // Ignorar comentarios y líneas vacías
            if (trimmed.isNotEmpty() && !trimmed.startsWith("#")) {
                val parts = trimmed.split("=", limit = 2)
                if (parts.size == 2) {
                    val key = parts[0].trim()
                    val value = parts[1].trim()

                    when (key) {
                        "SPOTIFY_CLIENT_ID" -> {
                            clientId = value
                        }
                        "SPOTIFY_CLIENT_SECRET" -> {
                            clientSecret = value
                        }
                    }
                }
            }
        }

        Log.d(TAG, "Configuración cargada correctamente")
    }

    fun getClientId(): String = clientId
    fun getClientSecret(): String = clientSecret
}
