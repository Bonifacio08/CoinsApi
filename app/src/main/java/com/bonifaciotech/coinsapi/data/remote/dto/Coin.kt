package com.bonifaciotech.coinsapi.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coin(
    val monedaId : Int = 0,
    val descripcion : String = "",
    val valor : Double,
    val imageUrl : String = ""
)