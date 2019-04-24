package me.kevincampos.ratesnow.local.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import me.kevincampos.ratesnow.local.dao.CurrencyDao
import me.kevincampos.ratesnow.local.dao.ExchangeRateDao
import me.kevincampos.ratesnow.local.model.CurrencyEntity
import me.kevincampos.ratesnow.local.model.ExchangeRateEntity

@Database(entities = [CurrencyEntity::class, ExchangeRateEntity::class], version = 1)
abstract class RatesNowDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
    abstract fun exchangeRateDao(): ExchangeRateDao

    companion object {

        private var INSTANCE: RatesNowDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): RatesNowDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            RatesNowDatabase::class.java,
                            "rates_now.db"
                        ).build()
                    }
                    return INSTANCE as RatesNowDatabase
                }
            }
            return INSTANCE as RatesNowDatabase
        }
    }

}