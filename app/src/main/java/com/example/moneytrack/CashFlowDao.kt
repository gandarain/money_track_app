package com.example.moneytrack

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CashFlowDao {
    @Insert
    suspend fun insert(cashFlowEntity: CashFlowEntity)

    @Update
    suspend fun update(cashFlowEntity: CashFlowEntity)

    @Delete
    suspend fun delete(cashFlowEntity: CashFlowEntity)

    @Query("SELECT * FROM `cash-flow-table`")
    // flow is used to hold a value that can change at runtime
    fun fetchAllCashFlow(): Flow<List<CashFlowEntity>>

    @Query("SELECT * FROM `cash-flow-table` WHERE id=:id")
    fun fetchCashFlowById(id: Int): Flow<CashFlowEntity>
}
