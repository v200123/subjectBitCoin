package com.lastcoffee.subjectbitcoin.bean

data class BaseRepose<T>(
    val code: String,
    val data:T,
    val msg: String
)

