package com.example.apispotify

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Field
import retrofit2.http.Query

interface SpotifyAuthService {
    @POST("api/token")
    @FormUrlEncoded
    fun getToken(
        @Field("grant_type") grantType: String = "client_credentials",
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): Call<TokenResponse>
}

interface SpotifyService {
    @GET("v1/search")
    fun searchTrack(
        @Header("Authorization") token: String,
        @Query("q") query: String,
        @Query("type") type: String = "track"
    ): Call<SpotifySearchResponse>
}

data class TokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int
)