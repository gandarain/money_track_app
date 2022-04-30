package com.example.moneytrack

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CashFlowEntity::class], version = 1)
abstract class CashFlowDatabase: RoomDatabase() {
    abstract fun cashFlowDao(): CashFlowDao

    companion object {
        @Volatile
        private var INSTANCE: CashFlowDatabase? = null

        fun getInstance(context: Context): CashFlowDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CashFlowDatabase::class.java,
                        "cash_flow_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}