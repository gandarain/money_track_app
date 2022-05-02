package com.example.moneytrack

import java.text.NumberFormat
import java.util.*

object Utils {
    fun convertToRupiah(number: Int): String {
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }
}