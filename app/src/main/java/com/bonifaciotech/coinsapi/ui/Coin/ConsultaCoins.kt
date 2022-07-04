package com.bonifaciotech.coinsapi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ConsultaCoins(
    //viewModel: exchangeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Consulta Coins                                     ")

                    FloatingActionButton(
                        onClick = {

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
                items(state.moneda){ moneda ->
                    NameItem(nameData = moneda, {})
                }
            }

            if (state.isLoading)
                CircularProgressIndicator()

        }


    }
}