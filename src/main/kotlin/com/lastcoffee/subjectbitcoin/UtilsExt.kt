package com.lastcoffee.subjectbitcoin

import java.math.BigDecimal
import java.math.RoundingMode

fun Double.toBig() = BigDecimal.valueOf(this)

operator fun BigDecimal.plus(a:BigDecimal) = this.add(a)
operator fun BigDecimal.minus(a:BigDecimal) = this.subtract(a)
operator fun BigDecimal.times(a:BigDecimal) = this.multiply(a)
operator fun BigDecimal.div(a:BigDecimal) = this.divide(a,RoundingMode.HALF_UP)