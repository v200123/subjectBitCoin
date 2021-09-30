package com.lastcoffee.subjectbitcoin.bean

class NewPriceBean : ArrayList<NewPriceBeanItem>()

data class NewPriceBeanItem(
    val high24h: String,
    val idxPx: Double,
    val instId: String,
    val low24h: String,
    val open24h: String,
    val sodUtc0: String,
    val sodUtc8: String,
    val ts: String
)