package com.bonifaciotech.coinsapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonifaciotech.coinsapi.data.NameApi
import com.bonifaciotech.coinsapi.data.remote.dto.NameData
import com.bonifaciotech.coinsapi.ui.theme.CoinsApiTheme
import com.bonifaciotech.coinsapi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    NameListScreen()
}

@Composable
fun NameListScreen(
    viewModel: exchangeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "My Api")
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

@Composable
fun NameItem(
    nameData: NameData,
    onClick : (NameData) -> Unit
) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clickable { onClick(nameData) }


    ) {
        Text(
            text = "${nameData.monedaId}  (${nameData.descripcion.orEmpty()})",
            style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Ellipsis
        )
        /* Text(

             text = "${exchange.description} ",
             style = MaterialTheme.typography.body1,
             overflow = TextOverflow.Ellipsis
         )*/
        Text(
            text = "${nameData.imageUrl} ",
            style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Ellipsis
        )
    }

}

class NameRepository @Inject constructor(
    private val api : NameApi
) {

    fun getName(): Flow<Resource<List<NameData>>> = flow {
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
}

data class NameListState(
    val isLoading: Boolean = false,
    val moneda: List<NameData> = emptyList(),
    val error: String = ""
)

@HiltViewModel
class exchangeViewModel @Inject constructor(
    private val nameRepository: NameRepository
): ViewModel(){

    private var _state = mutableStateOf(NameListState())
    val state: State<NameListState> = _state

    init {
        nameRepository.getName().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = NameListState(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = NameListState(moneda = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = NameListState(error = result.message ?: "Error desconocido")
                }
            }
        }.launchIn(viewModelScope)
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CoinsApiTheme {
        Greeting()
    }
}