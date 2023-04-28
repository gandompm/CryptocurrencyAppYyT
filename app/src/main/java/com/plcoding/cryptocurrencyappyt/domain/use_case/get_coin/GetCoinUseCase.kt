package com.plcoding.cryptocurrencyappyt.domain.use_case.get_coin

import com.plcoding.cryptocurrencyappyt.commen.Resource
import com.plcoding.cryptocurrencyappyt.data.remote.dto.toCoin
import com.plcoding.cryptocurrencyappyt.data.remote.dto.toCoinDetail
import com.plcoding.cryptocurrencyappyt.domain.model.Coin
import com.plcoding.cryptocurrencyappyt.domain.model.CoinDetail
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

// a use cas for getting Coin details
// injecting repo interface
class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
){


    // we need to pass the coinId
    operator fun invoke(coinId : String) : Flow<Resource<CoinDetail>> = flow {

        try {

            emit(Resource.Loading<CoinDetail>())
            // map all CoinDto to CoinDetail
            val coin = repository.getCoinDetails(coinId).toCoinDetail()
            emit(Resource.Success<CoinDetail>(coin))

        }
        // if we get a response code that doesn't start with 2
        catch (e: HttpException){
            emit(Resource.Error<CoinDetail>(e.message() ?: "An unexpected error occurred"))
        }
        // if our repo can not talk to remote api, e.g. internet connection error
        catch (e: IOException){
            emit(Resource.Error<CoinDetail>("couldn't reach server. Check your internet connection"))
        }
    }

}