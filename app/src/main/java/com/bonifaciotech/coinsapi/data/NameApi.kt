package com.bonifaciotech.coinsapi.data

import com.bonifaciotech.coinsapi.data.remote.dto.NameData
import retrofit2.http.GET
import retrofit2.http.Path

interface NameApi {
    @GET("/v1/Coins")//     /Coins
    suspend fun getName(): List<NameData>

    @GET("/v1/Coins/{monedaId}")//     /Coins
    suspend fun getNames(@Path("monedaId") nameId: String): NameData
}