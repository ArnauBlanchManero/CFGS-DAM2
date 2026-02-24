package com.example.apispotify

import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    // Configuración de Retrofit para autenticación
    private val authRetrofit = Retrofit.Builder()
        .baseUrl("https://accounts.spotify.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Configuración de Retrofit para búsqueda
    private val apiRetrofit = Retrofit.Builder()
        .baseUrl("https://api.spotify.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val authService = authRetrofit.create(SpotifyAuthService::class.java)
    private val searchService = apiRetrofit.create(SpotifyService::class.java)

    // Tus credenciales de Spotify (obtenidas en https://developer.spotify.com/dashboard)
    private val clientId = "TU_CLIENT_ID_AQUI"
    private val clientSecret = "TU_CLIENT_SECRET_AQUI"

    private var currentToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener referencias a los elementos de la UI
        val searchInput = findViewById<EditText>(R.id.searchInput)
        val searchButton = findViewById<Button>(R.id.searchButton)
        val resultSong = findViewById<TextView>(R.id.resultSong)

        // Obtener token al iniciar
        resultSong.text = "Obteniendo autenticación..."
        getAccessToken(resultSong)

        // Configurar el click listener del botón
        searchButton.setOnClickListener {
            val songName = searchInput.text.toString().trim()

            if (songName.isNotEmpty()) {
                if (currentToken != null) {
                    searchSong(songName, resultSong)
                } else {
                    resultSong.text = "Error: No se pudo obtener autenticación.\nVerifica tu Client ID y Client Secret"
                }
            } else {
                resultSong.text = "Por favor escribe el nombre de una canción"
            }
        }
    }

    private fun getAccessToken(resultTextView: TextView) {
        if (clientId == "TU_CLIENT_ID_AQUI" || clientSecret == "TU_CLIENT_SECRET_AQUI") {
            resultTextView.text = """
                ❌ ERROR: Credenciales no configuradas
                
                1. Ve a https://developer.spotify.com/dashboard
                2. Crea una app o usa una existente
                3. Copia tu Client ID y Client Secret
                4. Reemplaza en MainActivity.kt:
                   - clientId = "TU_CLIENT_ID_AQUI"
                   - clientSecret = "TU_CLIENT_SECRET_AQUI"
            """.trimIndent()
            return
        }

        val credentials = "$clientId:$clientSecret"
        val encodedCredentials = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

        val call = authService.getToken(clientId = clientId, clientSecret = clientSecret)

        call.enqueue(object : Callback<TokenResponse> {
            override fun onResponse(
                call: Call<TokenResponse>,
                response: Response<TokenResponse>
            ) {
                if (response.isSuccessful) {
                    val tokenResponse = response.body()
                    currentToken = "Bearer ${tokenResponse?.access_token}"
                    resultTextView.text = "✓ Autenticación exitosa\nEscribe el nombre de una canción"
                } else {
                    resultTextView.text = "Error en autenticación: ${response.code()}\n${response.message()}"
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                resultTextView.text = "Error de conexión: ${t.message}"
            }
        })
    }

    private fun searchSong(query: String, resultTextView: TextView) {
        resultTextView.text = "Buscando..."

        val call = searchService.searchTrack(currentToken!!, query)

        call.enqueue(object : Callback<SpotifySearchResponse> {
            override fun onResponse(
                call: Call<SpotifySearchResponse>,
                response: Response<SpotifySearchResponse>
            ) {
                if (response.isSuccessful) {
                    val searchResult = response.body()
                    val tracks = searchResult?.tracks?.items

                    if (tracks != null && tracks.isNotEmpty()) {
                        val firstTrack = tracks[0]

                        // Construir información de la canción
                        val artistNames = firstTrack.artists.joinToString(", ") { it.name }
                        val durationMinutes = firstTrack.durationMs / 60000
                        val durationSeconds = (firstTrack.durationMs % 60000) / 1000
                        val releaseDate = firstTrack.album?.releaseDate ?: "Desconocida"

                        val resultText = """
                            🎵 ${firstTrack.name}
                            👤 ${artistNames}
                            💿 ${firstTrack.album?.name ?: "Desconocido"}
                            📅 ${releaseDate}
                            ⏱️  ${durationMinutes}:${String.format("%02d", durationSeconds)}
                            ⭐ Popularidad: ${firstTrack.popularity}/100
                            ${if (firstTrack.isExplicit) "🔞 Contiene lenguaje explícito" else ""}
                        """.trimIndent()

                        resultTextView.text = resultText
                    } else {
                        resultTextView.text = "No se encontró ninguna canción con ese nombre"
                    }
                } else {
                    resultTextView.text = "Error en búsqueda: ${response.code()}\n${response.message()}"
                }
            }

            override fun onFailure(call: Call<SpotifySearchResponse>, t: Throwable) {
                resultTextView.text = "Error de conexión: ${t.message}"
            }
        })
    }
}