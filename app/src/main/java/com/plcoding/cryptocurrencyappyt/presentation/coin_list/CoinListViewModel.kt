package com.plcoding.cryptocurrencyappyt.presentation.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptocurrencyappyt.commen.Resource
import com.plcoding.cryptocurrencyappyt.domain.use_case.get_coin.GetCoinUseCase
import com.plcoding.cryptocurrencyappyt.domain.use_case.get_coins.GetCoinsUseCase
import com.plcoding.cryptocurrencyappyt.presentation.coin_list.CoinListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


/**
 * we moved in this architecture
 * most of our business logic
 * to our use cases
 *
 * the main purpose of viewmodel is to
 * maintain the state
 *
 * for keeping the UI state
 * when we rotate the screen, or change the language
 */
@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel(){

    /**
     * the reason for having a private and public instance
     * of the state is that:
     *
     * we don't want to be able to modify the state of it
     * in composable
     *
     * just to make sure only view model touches it
     */
    private val _state = mutableStateOf(CoinListState())
    val state: State<CoinListState> = _state

    init {
        getCoins()
    }

    private fun getCoins(){
        /**
         * getCoinUseCase is a class
         * but sice we overrided the invoke function
         * we can call the use case like a function
         */
        getCoinsUseCase().onEach {
            result ->
                when(result){
                    is Resource.Success -> {
                        _state.value = CoinListState(
                            coins = result.data ?: emptyList()
                        )
                    }
                    is Resource.Error -> {
                        _state.value = CoinListState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = CoinListState(isLoading = true)
                    }
                }

            // we lunch the coroutine in the view model scope
        }.launchIn(viewModelScope)
    }

}