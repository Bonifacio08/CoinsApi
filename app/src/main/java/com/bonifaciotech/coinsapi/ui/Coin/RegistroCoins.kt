package com.bonifaciotech.coinsapi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bonifaciotech.coinsapi.ui.Coin.coinViewModel

//Falta codigo dentro de la funcion ()
@Composable
fun RegistroCoins(
    viewModel: coinViewModel = hiltViewModel()
) {


    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Registro de Coins")})
        }



    ) {
        Column(modifier = Modifier
            .padding(it)
            .padding(8.dp)) {


            OutlinedTextField(
                label = {
                    Text(text = "Moneda:")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
                },
                value = viewModel.descrip,
                onValueChange ={viewModel.descrip = it},
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                label = {
                    Text(text = "Precio")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.MonetizationOn, contentDescription = null)
                },
                value = viewModel.Precio,
                onValueChange = {viewModel.Precio = it},
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if(viewModel.descrip.isNullOrEmpty() || viewModel.Precio.isNullOrEmpty()){
                        return@Button
                    }

                    viewModel.guardar()
                }) {
                Text(text = "Guardar")
            }
        }
    }
}
