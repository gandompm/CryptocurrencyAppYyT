package com.plcoding.cryptocurrencyappyt.domain.repository

import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDetailDto
import com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDto
import com.plcoding.cryptocurrencyappyt.domain.model.Coin
import com.plcoding.cryptocurrencyappyt.domain.model.CoinDetail


/**
 * functions that we want to have
 * from either the api
 * or the data base
 */
interface CoinRepository {


    suspend fun getCoins() : List<CoinDto>

    suspend fun getCoinDetails(id: String): CoinDetailDto
}