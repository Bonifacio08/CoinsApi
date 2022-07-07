package com.bonifaciotech.coinsapi.ui.Coin

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonifaciotech.coinsapi.CoinRepository
import com.bonifaciotech.coinsapi.NameListState
import com.bonifaciotech.coinsapi.data.remote.dto.Coin
import com.bonifaciotech.coinsapi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class coinViewModel @Inject constructor(
    private val CoinRepository: CoinRepository
): ViewModel() {

    var descrip by mutableStateOf("")
    var Precio by mutableStateOf("")


    private  var _state = mutableStateOf(NameListState())
    val state: State<NameListState> = _state

    fun guardar(){
        viewModelScope.launch {
            CoinRepository.postCoi(
                Coin(

                    descripcion = descrip,
                    valor = Precio.toDouble()
                )
            )
        }
    }

    init {
        CoinRepository.getName().onEach { result ->
            when(result){
                is Resource.Loading -> {
                    _state.value = NameListState(isLoading = true)
                }

                is  Resource.Success -> {
                    _state.value = NameListState(Moneda = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value = NameListState(error = result.message ?: "Error.....")
                }
            }
        }.launchIn(viewModelScope)
    }

}