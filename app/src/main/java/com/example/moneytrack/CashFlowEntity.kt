package com.example.moneytrack

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cash-flow-table")
data class CashFlowEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val type: String = "",
    val date: String = "",
    val amount: String = ""
)
