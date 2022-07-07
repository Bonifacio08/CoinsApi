package com.bonifaciotech.coinsapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bonifaciotech.coinsapi.data.NameApi
import com.bonifaciotech.coinsapi.data.remote.dto.Coin
import com.bonifaciotech.coinsapi.ui.theme.CoinsApiTheme
import com.bonifaciotech.coinsapi.util.Resource
import com.bonifaciotech.coinsapi.util.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoinsApiTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = Screen.ConsultaCoins.route){

        composable(route = Screen.ConsultaCoins.route){
            ConsultaCoins(IrRegistro = {navHostController.navigate(Screen.RegistroCoins.route)})
        }

        composable(route = Screen.RegistroCoins.route){
            RegistroCoins()
        }
    }

    //RegistroCoins()
    //ConsultaCoins()
}


class CoinRepository @Inject constructor(
    private val api : NameApi

) {
    fun getName(): Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading()) //indicar que estamos cargando

            val nam = api.getName()//descarga las monedas de internet, se supone quedemora algo

            emit(Resource.Success(nam)) //indicar que se cargo correctamente y pasarle las monedas
        } catch (e: HttpException) {
            //error general HTTP
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            //debe verificar tu conexion a internet
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    suspend fun postCoi(coin: Coin){
        api.agregarCoin(coin)

    }
}

    //

    //




data class NameListState(
    val isLoading: Boolean = false,
    val Moneda: List<Coin> = emptyList(),
    val error: String = ""
)

@Composable
fun NameItem(
    coin: Coin,
    onClick : (Coin) -> Unit
) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clickable { onClick(coin) }


    ) {
        Text(
            text = "${coin.monedaId}) ",
            style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "${coin.descripcion.orEmpty()} ",
            style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "${coin.valor} ",
            style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Ellipsis
        )
    }

}

//------------------------------------------------------


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CoinsApiTheme {
        Greeting()
    }
}