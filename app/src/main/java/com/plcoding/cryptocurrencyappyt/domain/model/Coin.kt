package com.plcoding.cryptocurrencyappyt.domain.model


/**
 * we don't need all the data from CoinDto class
 * so we create a Coin class
 * with all attributes that we want to display later
 */
data class Coin(
    val id: String,
    val is_active: Boolean,
     val name: String,
    val rank: Int,
    val symbol: String
)
