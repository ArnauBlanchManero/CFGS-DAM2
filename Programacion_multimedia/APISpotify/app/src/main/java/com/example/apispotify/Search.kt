package com.example.apispotify

import com.google.gson.annotations.SerializedName

// Respuesta de búsqueda de canciones
data class SpotifySearchResponse(
    val tracks: TrackItems
)

data class TrackItems(
    val items: List<Track>
)

data class Track(
    val id: String,
    val name: String,
    val artists: List<Artist>,
    @SerializedName("duration_ms")
    val durationMs: Int,
    val popularity: Int,
    @SerializedName("explicit")
    val isExplicit: Boolean,
    val album: Album?
)

data class Artist(
    val name: String,
    val id: String? = null,
    val genres: List<String>? = null,
    val popularity: Int? = null
)

data class Album(
    val id: String,
    val name: String,
    @SerializedName("release_date")
    val releaseDate: String?
)