package com.bonifaciotech.coinsapi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bonifaciotech.coinsapi.ui.Coin.coinViewModel

@Composable
fun ConsultaCoins(
    IrRegistro: ()-> Unit,
    viewModel: coinViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Consulta Coins                                     ")

                    FloatingActionButton(
                        onClick = {
                            IrRegistro()
                        }) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = null)
                    }
                })
        }

    ) {

        Column(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize()){
                items(state.Moneda){ Moneda ->
                    NameItem(coin = Moneda, {})
                }
            }

            if (state.isLoading)
                CircularProgressIndicator()

        }


    }
}