package com.plcoding.cryptocurrencyappyt.domain.model

import com.plcoding.cryptocurrencyappyt.data.remote.dto.TeamMember

class CoinDetail(
    val coinId: String,
    val name: String,
    val description: String,
    val symbol: String,
    val rank: Int,
    val isActive: Boolean,
    val tags: List<String>,
    val teams: List<TeamMember> ?= null
)