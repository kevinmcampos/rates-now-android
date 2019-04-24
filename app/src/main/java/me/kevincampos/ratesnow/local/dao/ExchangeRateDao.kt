package me.kevincampos.ratesnow.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import me.kevincampos.ratesnow.local.database.ExchangeRateConstants.TABLE_NAME
import me.kevincampos.ratesnow.local.model.ExchangeRateEntity

@Dao
abstract class ExchangeRateDao {

    @Query("SELECT * FROM $TABLE_NAME")
    @JvmSuppressWildcards
    abstract fun getExchangeRates(): List<ExchangeRateEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertExchangeRates(exchangeRates: List<ExchangeRateEntity>)

}