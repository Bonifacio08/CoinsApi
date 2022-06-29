package com.bonifaciotech.coinsapi.data.remote.dto

data class NameData(
    val monedaId : Int = 0,
    val descripcion : String = "",
    val valor : Double,
    val imageUrl : String = ""
)