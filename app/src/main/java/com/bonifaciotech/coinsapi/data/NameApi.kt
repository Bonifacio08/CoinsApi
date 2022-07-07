package com.bonifaciotech.coinsapi.data

import com.bonifaciotech.coinsapi.data.remote.dto.Coin
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NameApi {
    @GET("/Coins")//  /Coins
    suspend fun getName(): List<Coin>

    @GET("/Coins/{monedaId}")//     /Coins
    suspend fun getNames(@Path("monedaId") nameId: String): Coin

    @POST("/Coins")
    suspend fun agregarCoin(@Body coin: Coin): Coin
}