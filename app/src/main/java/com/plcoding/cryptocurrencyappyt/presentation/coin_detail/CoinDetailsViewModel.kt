package com.plcoding.cryptocurrencyappyt.presentation.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptocurrencyappyt.commen.Constants
import com.plcoding.cryptocurrencyappyt.commen.Resource
import com.plcoding.cryptocurrencyappyt.domain.use_case.get_coin.GetCoinUseCase
import com.plcoding.cryptocurrencyappyt.domain.use_case.get_coins.GetCoinsUseCase
import com.plcoding.cryptocurrencyappyt.presentation.coin_list.CoinListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


/**
 * savedStateHandle is a bundle and contains information
 * about the safe state
 *
 * we can use that to restore app
 * from process death
 *
 * it will also contain navigation parameters
 */
@HiltViewModel
class CoinDetailsViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(){


    private val _state = mutableStateOf(CoinDetailState())
    val state: State<CoinDetailState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID) ?.let { coinId ->
            getCoinDetail(coinId)
        }
    }

    private fun getCoinDetail(coinId : String){
        /**
         * getCoinUseCase is a class
         * but sice we overrided the invoke function
         * we can call the use case like a function
         */
        getCoinUseCase(coinId = coinId).onEach {
            result ->
                when(result){
                    is Resource.Success -> {
                        _state.value = CoinDetailState(
                            coin = result.data
                        )
                    }
                    is Resource.Error -> {
                        _state.value = CoinDetailState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = CoinDetailState(isLoading = true)
                    }
                }

            // we lunch the coroutine in the view model scope
        }.launchIn(viewModelScope)
    }

}