package com.bonifaciotech.coinsapi.util

sealed class Screen(val route: String) {
    object RegistroCoins : Screen("RegistroCoins")
    object ConsultaCoins : Screen("ConsultaCoins")
}