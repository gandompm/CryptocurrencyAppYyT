package com.plcoding.cryptocurrencyappyt.domain.use_case.get_coins

import com.plcoding.cryptocurrencyappyt.commen.Resource
import com.plcoding.cryptocurrencyappyt.data.remote.dto.toCoin
import com.plcoding.cryptocurrencyappyt.domain.model.Coin
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

// a use cas for getting all coins
// injecting repo interface, not implementation of the repo
class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
){


    // there is always one major feature for a single use case
    // otherwise the purpose of a use case isn't fulfilled anymore

    // if we override the invoke function, we can call the use case class as a function
    // @return Flow because we want to emit, multiple values over period of time

    // first we want to emit that we are loading use case for displaying progress bar
    // we want to emit if it is successful, so we want to attach our data
    // and if an error happen ,we want to emit the error
    operator fun invoke() : Flow<Resource<List<Coin>>> = flow {

        try {

            emit(Resource.Loading<List<Coin>>())
            // map all single CoinDto to Coin object
            val coins = repository.getCoins().map { it.toCoin() }
            emit(Resource.Success<List<Coin>>(coins))

        }
        // if we get a response code that doesn't start with 2
        catch (e: HttpException){
            emit(Resource.Error<List<Coin>>(e.message() ?: "An unexpected error occurred"))
        }
        // if our repo can not talk to remote api, e.g. internet connection error
        catch (e: IOException){
            emit(Resource.Error<List<Coin>>("couldn't reach server. Check your internet connection"))
        }
    }

}