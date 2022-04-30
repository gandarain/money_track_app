package com.example.moneytrack

import android.app.Application

class CashFlowApp: Application() {
    val db by lazy {
        CashFlowDatabase.getInstance(this)
    }
}