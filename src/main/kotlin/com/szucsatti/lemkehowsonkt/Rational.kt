package com.szucsatti.lemkehowsonkt

import org.jscience.mathematics.number.Rational

fun valueOf(dividend: Any): Rational =
    when(dividend){
        is Rational -> dividend
        is Long -> Rational.valueOf(dividend, 1L)
        is Int -> Rational.valueOf(dividend.toLong(), 1L)
        else -> throw UnsupportedOperationException()
    }

fun Rational.isGreaterOrEqualThan(that: Rational): Boolean = this.isGreaterThan(that) || this == that

fun Rational.isLessOrEqualThan(that : Rational) : Boolean = this.isLessThan(that) || this == that
